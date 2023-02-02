package com.music.musicStreamer.api.v1.controllers;

import com.music.musicStreamer.domain.repositories.MusicRepository;
import com.music.musicStreamer.domain.services.ImageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ImageControllerTest {
    @Mock
    private ImageService imageService = Mockito.mock(ImageService.class);

    @Mock
    private MusicRepository musicRepository = Mockito.mock(MusicRepository.class);

    @InjectMocks
    private ImageController imageController = new ImageController(imageService, musicRepository);

    @Test
    void testGetImage() throws IOException {
        when(imageService.getImage("pathName")).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<Object> response = imageController.getImage("pathName");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUploadImage_MusicFound() throws IOException {
        when(musicRepository.existsById(1)).thenReturn(true);
        when(imageService.uploadImage(Mockito.any(), eq(1))).thenReturn("Image uploaded successfully");
        ResponseEntity<Object> response = imageController.uploadImage(Mockito.mock(MultipartFile.class), 1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Image uploaded successfully", response.getBody());
    }

    @Test
    void testUploadImage_MusicNotFound() throws IOException {
        when(musicRepository.existsById(1)).thenReturn(false);
        ResponseEntity<Object> response = imageController.uploadImage(Mockito.mock(MultipartFile.class), 1);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Music not found", response.getBody());
    }
}
