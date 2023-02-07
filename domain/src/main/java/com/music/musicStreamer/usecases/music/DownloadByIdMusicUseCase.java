package com.music.musicStreamer.usecases.music;

import com.music.musicStreamer.entities.music.MusicDownload;
import com.music.musicStreamer.gateways.MusicGateway;

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
