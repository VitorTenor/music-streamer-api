package com.music.musicStreamer.core.util.factory;


import com.music.musicStreamer.api.v1.database.model.MusicModel;
import com.music.musicStreamer.api.v1.database.model.PlaylistModel;
import com.music.musicStreamer.api.v1.database.model.PlaylistMusicModel;
import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.playlist.PlaylistWithMusicEntity;
import com.music.musicStreamer.entity.playlistmusic.PlaylistMusicEntity;
import com.music.musicStreamer.usecase.image.GetImageByMusicIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaylistMusicFactory {
    private final GetImageByMusicIdUseCase getImageByMusicIdUseCase;

    public MusicEntity createMusic(MusicModel musicModel) {
        return new MusicEntity(
                musicModel.getId(),
                musicModel.getName(),
                musicModel.getArtist(),
                musicModel.getAlbum(),
                musicModel.getGenre(),
                getImageByMusicIdUseCase.execute(musicModel.getId()),
                musicModel.getPathName()
        );
    }

    public PlaylistWithMusicEntity toEntity(PlaylistModel model, List<MusicEntity> musicEntityList) {
        return new PlaylistWithMusicEntity(model.getId(), model.getName(), musicEntityList);
    }

    public PlaylistMusicModel toModel(PlaylistMusicEntity entity) {
        return new PlaylistMusicModel(
                entity.playlistId().intValue(),
                entity.userId().intValue(),
                entity.musicId().intValue(),
                new Date(), new Date()
        );
    }
}
