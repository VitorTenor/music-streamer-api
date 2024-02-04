package com.music.musicStreamer.core.util.validator;

import com.music.musicStreamer.api.v1.database.model.ImageModel;
import com.music.musicStreamer.enums.ImageMessages;
import com.music.musicStreamer.exception.ImageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class ImageValidator {
    private final Logger LOGGER = Logger.getLogger(ImageValidator.class.getName());

    public void validateIfImageIsNotNull(ImageModel image) {
        LOGGER.info("[ImageValidator] Validate if image is not null");

        if (image == null) throw new ImageException(ImageMessages.NOT_FOUND);

        LOGGER.info("[ImageValidator] Image is not null");
    }
}
