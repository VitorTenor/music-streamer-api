package com.music.musicStreamer.usecase.image;

import com.music.musicStreamer.gateway.ImageGateway;

import javax.inject.Named;

@Named
public class GetImageByPathNameUseCase {
    private final ImageGateway imageGateway;

    public GetImageByPathNameUseCase(ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public byte[] execute(String pathName) {
        return imageGateway.getByFileName(pathName);
    }
}
