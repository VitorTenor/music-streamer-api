package com.music.musicStreamer.usecase.music;

import com.music.musicStreamer.entity.music.MusicDownload;
import com.music.musicStreamer.gateway.MusicGateway;

import javax.inject.Named;


@Named
public class DownloadByIdMusicUseCase {
    private MusicGateway musicGateway;

    public DownloadByIdMusicUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }

    public MusicDownload execute(int id) throws Exception {
        return musicGateway.downloadMusic(id);
    }
}
