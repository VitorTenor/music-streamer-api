package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.request.ImageUpload;
import com.music.musicStreamer.entity.image.Image;
import com.music.musicStreamer.entity.image.ImageRequest;
import com.music.musicStreamer.usecase.image.GetImageUseCase;
import com.music.musicStreamer.usecase.image.UploadImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("music-streamer/v1/images")
public class ImageController {

    private final GetImageUseCase getImageUseCase;
    private final UploadImageUseCase uploadImageUseCase;
    private final Logger LOGGER = Logger.getLogger(ImageController.class.getName());

    @PostMapping(path = "/uploadByMusicId", consumes = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Image> uploadImageByMusicId(@ModelAttribute @Valid ImageUpload imageUpload) throws IOException {
        LOGGER.info("[ImageController] Upload image");
        return ResponseEntity.status(HttpStatus.OK).body(uploadImageUseCase.execute(imageUpload.toEntity()));
    }

    @GetMapping("/downloadByPathName/{getPathName}")
    public ResponseEntity<byte[]> getImage(@PathVariable(value = "getPathName", required = true) String getPathName) {
        LOGGER.info("[ImageController] Get image");
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(getImageUseCase.execute(getPathName));
    }
}
