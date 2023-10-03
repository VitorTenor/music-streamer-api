package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.model.ImageModel;
import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.entities.image.ImageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ImageFactory {
    private static @Value("${storage.image.mediaType}") String IMAGE_TYPE;
    private static @Value("${storage.image.url}") String IMAGE_URL;

    public ImageModel createImageModel(ImageRequest imageRequest, String pathName) {
        return new ImageModel(
                imageRequest.getId(),
                pathName + IMAGE_TYPE,
                new Date(),
                new Date()
        );
    }

    public Image createImage(ImageModel imageModel) {
        return new Image(
                imageModel.getMusicId(),
                imageModel.getPathName(),
                IMAGE_URL + imageModel.getPathName()
        );
    }
}
