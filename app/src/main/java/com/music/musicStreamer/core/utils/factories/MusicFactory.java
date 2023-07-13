package com.music.musicStreamer.core.utils.factories;

import com.music.musicStreamer.api.v1.models.MusicModel;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.music.MusicRequest;
import com.music.musicStreamer.usecases.image.GetImageByMusicIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MusicFactory {
    private final GetImageByMusicIdUseCase getImageByMusicIdUseCase;
    private static @Value("${storage.music.mediaType}") String MUSIC_TYPE;

    public Music createMusic(MusicModel musicModel) {
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
    public List<Music> createMusicList(List<MusicModel> musicModel) {
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

    public MusicModel createModel(MusicRequest musicRequest, String newFileName) {
        MusicModel musicModel = new MusicModel();
        musicModel.setName(musicRequest.getName());
        musicModel.setArtist(musicRequest.getArtist());
        musicModel.setAlbum(musicRequest.getAlbum());
        musicModel.setGenre(musicRequest.getGenre());
        musicModel.setPathName(newFileName + MUSIC_TYPE);
        musicModel.setCreated_at(new Date());
        musicModel.setUpdated_at(new Date());
        return musicModel;
    }
}
