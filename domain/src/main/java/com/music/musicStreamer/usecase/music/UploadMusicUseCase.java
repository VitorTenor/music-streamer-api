package com.music.musicStreamer.usecase.music;

import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.music.SaveMusicEntity;
import com.music.musicStreamer.gateway.MusicGateway;

import javax.inject.Named;

@Named
public class UploadMusicUseCase {

    private final MusicGateway musicGateway;

    public UploadMusicUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }

    public MusicEntity execute(SaveMusicEntity saveMusicEntity) throws Exception {
        return musicGateway.saveMusic(saveMusicEntity);
    }
}
