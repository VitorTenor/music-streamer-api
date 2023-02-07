package com.music.musicStreamer.usecases.image;

import com.music.musicStreamer.entities.image.ImageRequest;
import com.music.musicStreamer.gateways.ImageGateway;

import javax.inject.Named;

@Named
public class UploadImageUseCase {

    private final ImageGateway imageGateway;

    public UploadImageUseCase(final ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public String execute(final ImageRequest imageRequest) {
            try {
                this.imageGateway.saveImage(imageRequest);
                return "Image uploaded successfully";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
}
