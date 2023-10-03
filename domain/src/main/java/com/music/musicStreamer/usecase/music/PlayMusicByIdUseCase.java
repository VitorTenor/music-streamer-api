package com.music.musicStreamer.usecase.music;

import com.music.musicStreamer.gateway.MusicGateway;

import javax.inject.Named;
import java.io.IOException;

@Named
public class PlayMusicByIdUseCase {
    private final MusicGateway musicGateway;
    public PlayMusicByIdUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }
    public Object execute(final int id) throws IOException {
        return musicGateway.playMusic(id);
    }
}
