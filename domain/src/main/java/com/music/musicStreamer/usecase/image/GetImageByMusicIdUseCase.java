package com.music.musicStreamer.usecase.image;

import com.music.musicStreamer.entity.image.Image;
import com.music.musicStreamer.gateway.ImageGateway;

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
