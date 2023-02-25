package com.music.musicStreamer.usecases.playlist;

import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.gateways.PlaylistGateway;

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
