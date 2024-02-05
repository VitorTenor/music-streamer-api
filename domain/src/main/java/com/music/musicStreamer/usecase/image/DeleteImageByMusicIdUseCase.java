package com.music.musicStreamer.usecase.image;

import com.music.musicStreamer.gateway.ImageGateway;

import javax.inject.Named;

@Named
public class DeleteImageByMusicIdUseCase {
    private final ImageGateway imageGateway;

    public DeleteImageByMusicIdUseCase(ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public Boolean execute(int id) {
        return imageGateway.deleteByMusicId(id);
    }
}
