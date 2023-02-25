package com.music.musicStreamer.usecases.image;

import com.music.musicStreamer.gateways.ImageGateway;

import javax.inject.Named;

@Named
public class GetImageUseCase {
    private ImageGateway imageGateway;

    public GetImageUseCase(ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public byte[] execute(String imageName) {
        return imageGateway.getImage(imageName);
    }
}
