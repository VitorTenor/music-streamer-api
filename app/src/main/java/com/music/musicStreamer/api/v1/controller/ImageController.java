package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.openApi.ImageControllerOpenApi;
import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.entities.image.ImageRequest;
import com.music.musicStreamer.usecases.image.GetImageUseCase;
import com.music.musicStreamer.usecases.image.UploadImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("music-streamer/v1/images")
public class ImageController implements ImageControllerOpenApi {

    private final GetImageUseCase getImageUseCase;
    private final UploadImageUseCase uploadImageUseCase;
    private final Logger logger = Logger.getLogger(ImageController.class.getName());


    @PostMapping(path = "/{id}", consumes = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Image> uploadImage(@RequestParam(name = "image") MultipartFile file, @PathVariable("id") int id) throws IOException {
        ImageRequest imageRequest = new ImageRequest(file.getBytes(), id);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImageUseCase.execute(imageRequest));
    }

    @GetMapping("/{getPathName}")
    public ResponseEntity<byte[]> getImage(@PathVariable("getPathName") String getPathName) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(getImageUseCase.execute(getPathName));
    }
}
