package com.music.musicStreamer.usecases.music;

import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.gateways.MusicGateway;

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
