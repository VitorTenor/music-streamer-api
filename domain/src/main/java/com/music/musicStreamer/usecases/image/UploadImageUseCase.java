package com.music.musicStreamer.usecases.image;

import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.entities.image.ImageRequest;
import com.music.musicStreamer.gateways.ImageGateway;

import javax.inject.Named;

@Named
public class UploadImageUseCase {

    private final ImageGateway imageGateway;

    public UploadImageUseCase(final ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public Image execute(final ImageRequest imageRequest) {
        return imageGateway.saveImage(imageRequest);
    }
}
