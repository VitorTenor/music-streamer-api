package com.music.musicStreamer.usecase.playlistMusic;

import com.music.musicStreamer.entity.playlistmusic.PlaylistMusicEntity;
import com.music.musicStreamer.gateway.PlaylistMusicGateway;

import javax.inject.Named;

@Named
public class CreatePlaylistMusicUseCase {

    private final PlaylistMusicGateway gateway;

    public CreatePlaylistMusicUseCase(PlaylistMusicGateway gateway) {
        this.gateway = gateway;
    }

    public String execute(PlaylistMusicEntity entity) {
        return gateway.create(entity);
    }
}
