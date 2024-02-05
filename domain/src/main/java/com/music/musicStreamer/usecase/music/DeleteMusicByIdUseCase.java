package com.music.musicStreamer.usecase.music;

import com.music.musicStreamer.gateway.MusicGateway;

import javax.inject.Named;

@Named
public class DeleteMusicByIdUseCase {
    private final MusicGateway musicGateway;

    public DeleteMusicByIdUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }

    public String execute(final Long id) {
        return musicGateway.deleteMusicById(id);
    }
}
