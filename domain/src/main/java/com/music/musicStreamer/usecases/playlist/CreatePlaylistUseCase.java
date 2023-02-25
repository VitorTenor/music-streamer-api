package com.music.musicStreamer.usecases.playlist;

import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.entities.playlist.PlaylistRequest;
import com.music.musicStreamer.gateways.PlaylistGateway;

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
