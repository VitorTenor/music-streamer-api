package com.music.musicStreamer.usecase.playlist;

import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import com.music.musicStreamer.entity.playlist.CreatePlaylistEntity;
import com.music.musicStreamer.gateway.PlaylistGateway;

import javax.inject.Named;

@Named
public class CreatePlaylistUseCase {
    private final PlaylistGateway playlistGateway;

    public CreatePlaylistUseCase(PlaylistGateway playlistGateway) {
        this.playlistGateway = playlistGateway;
    }

    public PlaylistEntity execute(CreatePlaylistEntity createPlaylistEntity) {
        return playlistGateway.create(createPlaylistEntity);
    }
}
