package com.music.musicStreamer.usecase.music;

import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.gateway.MusicGateway;

import javax.inject.Named;
import java.util.List;

@Named
public class GetAllMusicsUseCase {
    private final MusicGateway musicGateway;

    public GetAllMusicsUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }
    public List<MusicEntity> execute() {
        return musicGateway.getAllMusics();
    }
}
