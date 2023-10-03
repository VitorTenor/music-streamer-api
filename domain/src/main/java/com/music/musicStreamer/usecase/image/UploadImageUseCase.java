package com.music.musicStreamer.usecase.image;

import com.music.musicStreamer.entity.image.Image;
import com.music.musicStreamer.entity.image.ImageRequest;
import com.music.musicStreamer.gateway.ImageGateway;

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
