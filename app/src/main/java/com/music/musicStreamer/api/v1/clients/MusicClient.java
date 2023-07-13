package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.MusicModel;
import com.music.musicStreamer.api.v1.repositories.MusicRepository;
import com.music.musicStreamer.core.GenerateName;
import com.music.musicStreamer.core.utils.validators.ImageValidator;
import com.music.musicStreamer.core.utils.validators.MusicValidator;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.music.MusicDownload;
import com.music.musicStreamer.entities.music.MusicRequest;
import com.music.musicStreamer.exceptions.MusicException;
import com.music.musicStreamer.gateways.MusicGateway;
import com.music.musicStreamer.usecases.image.DeleteImageByMusicIdUseCase;
import com.music.musicStreamer.usecases.image.GetImageByMusicIdUseCase;

import com.music.musicStreamer.usecases.playlistMusic.DeleteMusicFromPlaylistUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MusicClient implements MusicGateway {
    private final MusicValidator musicValidator;
    private final GenerateName generateName;
    private final MusicRepository musicRepository;
    private final GetImageByMusicIdUseCase getImageByMusicIdUseCase;
    private final DeleteImageByMusicIdUseCase deleteImageByMusicIdUseCase;
    private final DeleteMusicFromPlaylistUseCase deleteMusicFromPlaylistUseCase;
    private static @Value("${storage.music.mediaType}") String MUSIC_TYPE;
    private static @Value("${storage.music.path}") String MUSIC_PATH;


    @Override
    @Transactional
    public Music saveMusic(MusicRequest musicRequest) {
        musicValidator.validateMusic(musicRequest);

        String newFileName = generateName.randomName();

        saveInStorage(musicRequest, newFileName);

        return saveInDatabase(toModel(musicRequest, newFileName)).toEntity();
    }

    private void saveInStorage(MusicRequest musicRequest, String newFileName) {
        try {
            Path path = Path.of(MUSIC_PATH + newFileName + MUSIC_TYPE);
            Files.write(path, musicRequest.getMusic());
        } catch (IOException e) {
            throw new MusicException("Error to save music");
        }
    }

    private MusicModel saveInDatabase(MusicModel music) {
        return musicRepository.save(music);
    }


    @Override
    public List<Music> getAllMusics() {
        try {
            return listToMusic(musicRepository.findAll());
        } catch (Exception e) {
            throw new MusicException("Error to get musics");
        }
    }
    @Override
    public Object playMusic(int id) {
        MusicModel musicModel = musicRepository.findById(id).orElseThrow(() -> new MusicException("Music not found"));
        try {
            File file = new File(MUSIC_PATH + musicModel.getPathName());
            return new InputStreamResource(new FileInputStream(file));
        } catch (Exception e) {
            throw new MusicException("Error to play music");
        }
    }

    @Override
    public MusicDownload downloadMusic(int id) {
        MusicModel musicModel = musicRepository.findById(id).orElseThrow(() -> new MusicException("Music not found"));
        File file = new File(MUSIC_PATH + musicModel.getPathName());
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return new MusicDownload(resource.getInputStream(), file);
        } catch (Exception e) {
            throw new MusicException("Error to download music");
        }
    }

    @Override
    public Music getMusicById(int id) {
        return toMusic(musicRepository.findById(id).orElseThrow(() -> new MusicException("Music not found")));
    }

    @Override
    @Transactional
    public String deleteMusicById(int id) {
        MusicModel musicModel = musicRepository.findById(id).orElseThrow(() -> new MusicException("Music not found"));
        try {
            Files.delete(Path.of(MUSIC_PATH + musicModel.getPathName()));
            musicRepository.deleteById(id);
            deleteImageByMusicIdUseCase.execute(id);
            deleteMusicFromPlaylistUseCase.execute(id);
            return "Music deleted";
        } catch (Exception e) {
            throw new MusicException("Error to delete music");
        }
    }



    private Music toMusic(MusicModel musicModel) {
        return new Music(
                musicModel.getId(),
                musicModel.getName(),
                musicModel.getArtist(),
                musicModel.getAlbum(),
                musicModel.getGenre(),
                getImageByMusicIdUseCase.execute(musicModel.getId()),
                musicModel.getPathName()
        );

    }
    private List<Music> listToMusic(List<MusicModel> musicModel) {
        List<Music> musicDTO = new ArrayList<>();
        for (MusicModel music : musicModel) {
            Music music2 = new Music(
                    music.getId(),
                    music.getName(),
                    music.getArtist(),
                    music.getAlbum(),
                    music.getGenre(),
                    getImageByMusicIdUseCase.execute(music.getId()),
                    music.getPathName()
            );
            musicDTO.add(music2);
        }
        return musicDTO;
    }

    public MusicModel toModel(MusicRequest musicRequest, String newFileName) {
        MusicModel musicModel = new MusicModel();
        musicModel.setName(musicRequest.getName());
        musicModel.setGenre(musicRequest.getGenre());
        musicModel.setArtist(musicRequest.getArtist());
        musicModel.setAlbum(musicRequest.getAlbum());
        musicModel.setPathName(newFileName + MUSIC_TYPE);
        musicModel.setCreated_at(new Date());
        musicModel.setUpdated_at(new Date());
        return musicModel;
    }
}
