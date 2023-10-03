package com.music.musicStreamer.usecase.music;

import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.gateway.MusicGateway;

import javax.inject.Named;

@Named
public class GetMusicByIdUseCase {
    private final MusicGateway musicGateway;
    public GetMusicByIdUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }
    public Music execute(final int id) {
        return musicGateway.getMusicById(id);
    }
}
