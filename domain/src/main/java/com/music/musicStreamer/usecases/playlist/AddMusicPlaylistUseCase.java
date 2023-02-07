package com.music.musicStreamer.usecases.playlist;

import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.gateways.PlaylistGateway;

import javax.inject.Named;

@Named
public class AddMusicPlaylistUseCase {

    private final PlaylistGateway playlistGateway;

    public AddMusicPlaylistUseCase(PlaylistGateway playlistGateway) {
        this.playlistGateway = playlistGateway;
    }

    public String execute(MusicPlaylistRequest musicPlaylistRequest) {
        return playlistGateway.addMusicToPlaylist(musicPlaylistRequest);
    }
}
