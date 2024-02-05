package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entity.image.ImageRequest;
import com.music.musicStreamer.enums.ImageMessages;
import com.music.musicStreamer.exception.ImageException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Schema(name = "Image Upload", description = "Request to upload an image")
public record ImageInput(
        @Schema(description = "Image", example = "image.jpg")
        @NotNull(message = "Image is required")
        MultipartFile image,
        @Schema(description = "Music id", example = "1")
        @NotNull(message = "Music id is required")
        int musicId
) {
    public ImageRequest toEntity() {
        try {
            var imageBytes = image.getBytes();
            return new ImageRequest(imageBytes, musicId);
        } catch (Exception e) {
            throw new ImageException(ImageMessages.READ_ERROR);
        }
    }
}
