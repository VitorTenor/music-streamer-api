package com.music.musicStreamer.usecase.music;

import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.music.MusicRequest;
import com.music.musicStreamer.gateway.MusicGateway;

import javax.inject.Named;

@Named
public class UploadMusicUseCase {

    private final MusicGateway musicGateway;

    public UploadMusicUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }

    public MusicEntity execute(MusicRequest musicRequest) throws Exception {
        return musicGateway.saveMusic(musicRequest);
    }
}
