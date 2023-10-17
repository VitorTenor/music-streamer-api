package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entity.image.ImageRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

public record ImageUpload(
        @NotNull(message = "Image is required")
        MultipartFile image,
        @NotNull(message = "Music id is required")
        int musicId
) {

    public ImageRequest toEntity() throws IOException {
        return new ImageRequest(image.getBytes(), musicId);
    }
}
