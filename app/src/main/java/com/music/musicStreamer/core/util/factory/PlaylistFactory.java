package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.database.model.PlaylistModel;
import com.music.musicStreamer.api.v1.database.model.PlaylistMusicModel;
import com.music.musicStreamer.entity.playlist.CreatePlaylistEntity;
import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaylistFactory {
    public PlaylistEntity toEntity(PlaylistModel playlistModel) {
        return new PlaylistEntity((long) playlistModel.getId(), playlistModel.getName(), List.of());
    }

    public PlaylistMusicModel createPlaylistMusicModel(MusicPlaylistRequest musicPlaylistRequest) {
        return new PlaylistMusicModel(musicPlaylistRequest.getPlaylistId(), musicPlaylistRequest.getUserId(), musicPlaylistRequest.getMusicId(), new Date(), new Date());
    }

    public PlaylistEntity toEntity(final PlaylistModel playlistModel, final List<Long> musicIds) {
        return new PlaylistEntity((long) playlistModel.getId(), playlistModel.getName(), musicIds);
    }

    public PlaylistModel toModel(CreatePlaylistEntity entity) {
        return new PlaylistModel(entity.name(), entity.userId(), new Date(), new Date());
    }
}
