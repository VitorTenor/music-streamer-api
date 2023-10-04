package com.music.musicStreamer.core.util.validator;

import com.music.musicStreamer.api.v1.model.ImageModel;
import com.music.musicStreamer.api.v1.repository.MusicRepository;
import com.music.musicStreamer.entity.image.ImageRequest;
import com.music.musicStreamer.enums.ImageMessages;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.exception.ImageException;
import com.music.musicStreamer.exception.MusicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageValidator {

    private final MusicRepository musicRepository;

    public void validateImage(ImageRequest imageRequest) {
        if (imageRequest.getImage() == null) throw new ImageException(ImageMessages.IMAGE_IS_REQUIRED);
        if (imageRequest.getId() == 0) throw new ImageException(ImageMessages.ID_IS_REQUIRED);
        if (!musicRepository.existsById(imageRequest.getId())) throw new MusicException(MusicMessages.NOT_FOUND);
    }

    public void validateIfImageIsNotNull(ImageModel image) {
        if (image == null) throw new ImageException(ImageMessages.NOT_FOUND);
    }
}