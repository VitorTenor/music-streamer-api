package com.music.musicStreamer.usecases.music;

import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.music.MusicRequest;
import com.music.musicStreamer.gateways.MusicGateway;

import javax.inject.Named;

@Named
public class UploadMusicUseCase {

    private final MusicGateway musicGateway;

    public UploadMusicUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }

    public Music execute(MusicRequest musicRequest) throws Exception {
        return musicGateway.saveMusic(musicRequest);
    }
}
