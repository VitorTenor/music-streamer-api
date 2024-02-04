package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.database.model.ImageModel;
import com.music.musicStreamer.api.v1.database.model.MusicModel;
import com.music.musicStreamer.entity.image.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ImageFactory {
    private @Value("${storage.image.mediaType}") String IMAGE_TYPE;
    private @Value("${storage.image.url}") String IMAGE_URL;
    private @Value("${storage.image.defaultImage}") String DEFAULT_IMAGE;

    public ImageModel createImageModel(MusicModel musicModel, String pathName) {
        return new ImageModel(
                musicModel,
                pathName + IMAGE_TYPE,
                new Date(),
                new Date()
        );
    }

    public Image createImage(ImageModel imageModel) {
        return new Image(
                imageModel.getMusic().getId(),
                imageModel.getPathName(),
                IMAGE_URL + imageModel.getPathName()
        );
    }

    public Image createDefaultImage() {
        return new Image(
                null,
                DEFAULT_IMAGE,
                IMAGE_URL + DEFAULT_IMAGE
        );
    }
}
