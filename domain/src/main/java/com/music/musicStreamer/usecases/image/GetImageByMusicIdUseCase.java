package com.music.musicStreamer.usecases.image;

import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.gateways.ImageGateway;

import javax.inject.Named;

@Named
public class GetImageByMusicIdUseCase {

    private final ImageGateway imageGateway;

    public GetImageByMusicIdUseCase(ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public Image execute(int id) {
        return imageGateway.getImageById(id);
    }
}
