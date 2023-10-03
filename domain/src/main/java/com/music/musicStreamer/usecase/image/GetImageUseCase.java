package com.music.musicStreamer.usecase.image;

import com.music.musicStreamer.gateway.ImageGateway;

import javax.inject.Named;

@Named
public class GetImageUseCase {
    private ImageGateway imageGateway;

    public GetImageUseCase(ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public byte[] execute(String imageName) {
        return imageGateway.getImageByFileName(imageName);
    }
}
