package com.music.musicStreamer.usecases.playlistMusic;

import com.music.musicStreamer.gateways.PlaylistMusicGateway;

import javax.inject.Named;

@Named
public class DeleteMusicFromPlaylistUseCase {
    private final PlaylistMusicGateway playlistMusicGateway;

    public DeleteMusicFromPlaylistUseCase(PlaylistMusicGateway playlistMusicGateway) {
        this.playlistMusicGateway = playlistMusicGateway;
    }

    public Boolean execute(int id) {
        return playlistMusicGateway.deleteMusicFromPlaylist(id);
    }
}
