package com.music.musicStreamer.usecase.playlist;

import com.music.musicStreamer.entity.playlist.Playlist;
import com.music.musicStreamer.entity.playlist.PlaylistRequest;
import com.music.musicStreamer.gateway.PlaylistGateway;

import javax.inject.Named;

@Named
public class CreatePlaylistUseCase {
    private final PlaylistGateway playlistGateway;

    public CreatePlaylistUseCase(PlaylistGateway playlistGateway) {
        this.playlistGateway = playlistGateway;
    }

    public Playlist execute(PlaylistRequest playlistRequest) throws Exception {
        return playlistGateway.createPlaylist(playlistRequest);
    }
}
