package com.music.musicStreamer.usecase.playlist;

import com.music.musicStreamer.entity.playlist.Playlist;
import com.music.musicStreamer.gateway.PlaylistGateway;

import javax.inject.Named;
import java.util.List;

@Named
public class GetPlaylistByUserIdUseCase {
    private final PlaylistGateway playlistGateway;

    public GetPlaylistByUserIdUseCase(PlaylistGateway playlistGateway) {
        this.playlistGateway = playlistGateway;
    }

    public List<Playlist> execute(int id) {
        return playlistGateway.getPlaylistByUserId(id);
    }
}
