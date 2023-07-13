package com.music.musicStreamer.core.utils.factories;

import com.music.musicStreamer.api.v1.models.ImageModel;
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
            return ImageModel
                    .builder()
                    .pathName(pathName + IMAGE_TYPE)
                    .musicId(imageRequest.getId())
                    .created_at(new Date())
                    .updated_at(new Date())
                    .build();
    }

    public Image createImage(ImageModel imageModel) {
            return new Image(
                    imageModel.getMusicId(),
                    imageModel.getPathName(),
                    IMAGE_URL + imageModel.getPathName()
            );
    }
}
