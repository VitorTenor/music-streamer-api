package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.MusicModel;
import com.music.musicStreamer.api.v1.repositories.MusicRepository;
import com.music.musicStreamer.core.GenerateName;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.music.MusicDownload;
import com.music.musicStreamer.entities.music.MusicRequest;
import com.music.musicStreamer.gateways.MusicGateway;
import com.music.musicStreamer.usecases.image.DeleteImageByMusicIdUseCase;
import com.music.musicStreamer.usecases.image.GetImageByMusicIdUseCase;

import com.music.musicStreamer.usecases.playlistMusic.DeleteMusicFromPlaylistUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

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
    private final GenerateName generateName;
    private final MusicRepository musicRepository;
    private final GetImageByMusicIdUseCase getImageByMusicIdUseCase;
    private final DeleteImageByMusicIdUseCase deleteImageByMusicIdUseCase;
    private final DeleteMusicFromPlaylistUseCase deleteMusicFromPlaylistUseCase;
    private @Value("${storage.music.path}") String MUSIC_PATH;
    private @Value("${storage.music.mediaType}") String MUSIC_TYPE;


    @Override
    public void saveMusic(MusicRequest musicRequest) {
        try {
            String newFileName = generateName.randomName();
            Path path = Path.of(MUSIC_PATH + newFileName + MUSIC_TYPE);
            Files.write(path, musicRequest.getMusic());
            musicRepository.save(toModel(musicRequest, newFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Music> getAllMusics() {
        List<MusicModel> musicModel = musicRepository.findAll();
        return listToMusic(musicModel);
    }
    @Override
    public Object playMusic(int id) throws IOException {
        MusicModel musicModel = musicRepository.findById(id).orElse(null);
        if (musicModel == null) {
            return null;
        }
        File file = new File(MUSIC_PATH + musicModel.getPathName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return resource;
    }

    @Override
    public MusicDownload downloadMusic(int id) throws IOException {
        MusicModel musicModel = musicRepository.findById(id).orElse(null);
        if (musicModel == null) {
            return null;
        }
        File file = new File(MUSIC_PATH + musicModel.getPathName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return new MusicDownload(resource.getInputStream(), file);
    }

    @Override
    public Music getMusicById(int id) {
        MusicModel musicModel = musicRepository.findById(id).orElse(null);
        if (musicModel == null) {
            return null;
        }
        return toMusic(musicModel);
    }

    @Override
    public String deleteMusicById(int id) {
        MusicModel musicModel = musicRepository.findById(id).orElse(null);
        if (musicModel == null) {
            return null;
        }
        try {
            Path path = Path.of(MUSIC_PATH + musicModel.getPathName());
            Files.delete(path);
            musicRepository.deleteById(id);
            deleteImageByMusicIdUseCase.execute(id);
            deleteMusicFromPlaylistUseCase.execute(id);
            return "Music deleted";
        } catch (Exception e) {
            throw new RuntimeException(e);
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
