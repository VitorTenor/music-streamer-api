package com.music.musicStreamer.controllers;

import com.music.musicStreamer.repositories.MusicRepository;
import com.music.musicStreamer.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;
    private final MusicRepository musicRepository;

    public ImageController(ImageService imageService, MusicRepository musicRepository) {
        this.imageService = imageService;
        this.musicRepository = musicRepository;
    }
    @GetMapping("/{getPathName}")
    public ResponseEntity getImage(@PathVariable("getPathName") String getPathName) throws IOException {
        return imageService.getImage(getPathName);
    }
    @PostMapping("/{id}")
    public ResponseEntity uploadImage(@RequestParam(name = "image")MultipartFile file, @PathVariable("id") Integer id) throws IOException {
        if(musicRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.OK).body(imageService.uploadImage(file, id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Music not found");
    }
}
