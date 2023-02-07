package com.music.musicStreamer.usecases.playlist;

import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.entities.playlist.PlaylistMusic;
import com.music.musicStreamer.gateways.PlaylistGateway;

import javax.inject.Named;

@Named
public class GetPlaylistByIdUseCase {
    private final PlaylistGateway playlistGateway;

    public GetPlaylistByIdUseCase(PlaylistGateway playlistGateway) {
        this.playlistGateway = playlistGateway;
    }

    public PlaylistMusic execute(int id) {
        return playlistGateway.getPlaylistById(id);
    }
}
