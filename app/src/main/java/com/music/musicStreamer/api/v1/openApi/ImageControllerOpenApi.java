package com.music.musicStreamer.api.v1.openApi;

import com.music.musicStreamer.entity.image.ImageEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Image Controller")
public interface ImageControllerOpenApi {
    @Operation(summary = "Upload image",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
            })
    ResponseEntity<ImageEntity> uploadImageByMusicId(@RequestParam(name = "image") MultipartFile file, @PathVariable("id") int id) throws IOException;
}
