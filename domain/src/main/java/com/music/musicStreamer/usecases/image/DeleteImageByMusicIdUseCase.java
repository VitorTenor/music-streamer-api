package com.music.musicStreamer.usecases.image;

import com.music.musicStreamer.gateways.ImageGateway;

import javax.inject.Named;

@Named
public class DeleteImageByMusicIdUseCase {
    private final ImageGateway imageGateway;

    public DeleteImageByMusicIdUseCase(ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public Boolean execute(int id) {
        return imageGateway.deleteImageByMusicId(id);
    }
}
