package com.music.musicStreamer.usecase.music;

import com.music.musicStreamer.gateway.MusicGateway;

import javax.inject.Named;

@Named
public class PlayMusicByIdUseCase {
    private final MusicGateway musicGateway;
    public PlayMusicByIdUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }
    public byte[] execute(final Long id) {
        return musicGateway.playMusic(id);
    }
}
