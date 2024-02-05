package com.music.musicStreamer.usecase.image;

import com.music.musicStreamer.entity.image.ImageEntity;
import com.music.musicStreamer.entity.image.UploadImageEntity;
import com.music.musicStreamer.gateway.ImageGateway;

import javax.inject.Named;

@Named
public class UploadImageUseCase {

    private final ImageGateway imageGateway;

    public UploadImageUseCase(final ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public ImageEntity execute(final UploadImageEntity uploadImageEntity) {
        return imageGateway.save(uploadImageEntity);
    }
}
