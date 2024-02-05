package com.music.musicStreamer.usecase.playlist;

import com.music.musicStreamer.entity.playlist.PlaylistWithMusicEntity;
import com.music.musicStreamer.gateway.PlaylistGateway;

import javax.inject.Named;

@Named
public class GetPlaylistByIdUseCase {
    private final PlaylistGateway playlistGateway;

    public GetPlaylistByIdUseCase(PlaylistGateway playlistGateway) {
        this.playlistGateway = playlistGateway;
    }

    public PlaylistWithMusicEntity execute(int id) {
        return playlistGateway.getPlaylistById(id);
    }
}
