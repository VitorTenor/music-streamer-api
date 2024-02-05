package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.database.model.ImageModel;
import com.music.musicStreamer.api.v1.database.model.MusicModel;
import com.music.musicStreamer.entity.image.ImageEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ImageFactory {
    private @Value("${storage.image.mediaType}") String IMAGE_TYPE;
    private @Value("${storage.image.url}") String IMAGE_URL;
    private @Value("${storage.image.defaultImage}") String DEFAULT_IMAGE;

    public ImageModel toModel(final MusicModel musicModel, final String pathName) {
        final var path = pathName + IMAGE_TYPE;
        return new ImageModel(
                musicModel,
                path,
                new Date(),
                new Date()
        );
    }

    public ImageEntity toEntity(final ImageModel imageModel) {
        final var url = IMAGE_URL + imageModel.getPathName();
        return new ImageEntity(
                imageModel.getMusic().getId(),
                imageModel.getPathName(),
                url
        );
    }

    public ImageEntity toEntityDefault() {
        return new ImageEntity(
                null,
                DEFAULT_IMAGE,
                IMAGE_URL + DEFAULT_IMAGE
        );
    }
}
