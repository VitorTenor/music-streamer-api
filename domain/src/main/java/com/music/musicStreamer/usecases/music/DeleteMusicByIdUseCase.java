package com.music.musicStreamer.usecases.music;

import com.music.musicStreamer.gateways.MusicGateway;

import javax.inject.Named;

@Named
public class DeleteMusicByIdUseCase {
    private final MusicGateway musicGateway;

    public DeleteMusicByIdUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }

    public String execute(int id) {
        return musicGateway.deleteMusicById(id);
    }
}
