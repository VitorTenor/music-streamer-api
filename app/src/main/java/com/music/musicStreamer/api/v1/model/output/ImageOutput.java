package com.music.musicStreamer.api.v1.model.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "Image Output", description = "Response of the image")
public class ImageOutput {
    @Schema(description = "Image id", example = "1")
    private final Integer id;
    @Schema(description = "Image path name", example = "image.jpg")
    private final String pathName;
    @Schema(description = "Image url", example = "http://localhost:8080/api/v1/image/1")
    private final String url;
}
