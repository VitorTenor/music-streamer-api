package com.music.musicStreamer.usecases.music;

import com.music.musicStreamer.entities.music.MusicRequest;
import com.music.musicStreamer.gateways.MusicGateway;

import javax.inject.Named;

@Named
public class UploadMusicUseCase {

    private final MusicGateway musicGateway;

    public UploadMusicUseCase(MusicGateway musicGateway) {
        this.musicGateway = musicGateway;
    }

    public String execute(MusicRequest musicRequest) {
        try {
            musicGateway.saveMusic(musicRequest);
            return "Music uploaded successfully";
        } catch (Exception e) {
            return "Error uploading music";
        }
    }
}
