package com.music.musicStreamer.usecase.playlistMusic;

import com.music.musicStreamer.gateway.PlaylistMusicGateway;

import javax.inject.Named;

@Named
public class DeleteMusicFromPlaylistUseCase {
    private final PlaylistMusicGateway playlistMusicGateway;

    public DeleteMusicFromPlaylistUseCase(PlaylistMusicGateway playlistMusicGateway) {
        this.playlistMusicGateway = playlistMusicGateway;
    }

    public Boolean execute(final Long id) {
        return playlistMusicGateway.deleteByMusicId(id);
    }
}
