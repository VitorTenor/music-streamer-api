package com.music.musicStreamer.core.util.factory;


import com.music.musicStreamer.api.v1.database.model.MusicModel;
import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.usecase.image.GetImageByMusicIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaylistMusicFactory {
    private final GetImageByMusicIdUseCase getImageByMusicIdUseCase;

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
}
