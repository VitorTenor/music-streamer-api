package com.music.musicStreamer.usecase.music;

import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.gateway.MusicGateway;

import javax.inject.Named;

@Named
public class GetMusicByIdUseCase {
    private final MusicGateway musicGateway;

    public GetMusicByIdUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }

    public MusicEntity execute(final Long id) {
        return this.musicGateway.getMusicById(id);
    }
}
