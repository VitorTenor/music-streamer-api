package com.music.musicStreamer.usecase.playlist;

import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.gateway.PlaylistGateway;

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
