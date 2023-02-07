package com.music.musicStreamer.usecases.music;

import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.gateways.MusicGateway;

import javax.inject.Named;
import java.util.List;

@Named
public class GetAllMusicsUseCase {
    private final MusicGateway musicGateway;

    public GetAllMusicsUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }
    public List<Music> execute() {
        return musicGateway.getAllMusics();
    }
}
