package com.music.musicStreamer.usecase.playlist;

import com.music.musicStreamer.entity.playlist.PlaylistMusicEntity;
import com.music.musicStreamer.gateway.PlaylistGateway;

import javax.inject.Named;

@Named
public class GetPlaylistByIdUseCase {
    private final PlaylistGateway playlistGateway;

    public GetPlaylistByIdUseCase(PlaylistGateway playlistGateway) {
        this.playlistGateway = playlistGateway;
    }

    public PlaylistMusicEntity execute(int id) {
        return playlistGateway.getPlaylistById(id);
    }
}
