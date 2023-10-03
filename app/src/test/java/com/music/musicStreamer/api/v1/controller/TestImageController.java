package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.entities.image.ImageRequest;
import com.music.musicStreamer.usecases.image.UploadImageUseCase;
import com.music.musicStreamer.usecases.image.GetImageUseCase;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("ImageController Class Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestImageController {
    @Mock
    private GetImageUseCase getImageUseCase;

    @Mock
    private UploadImageUseCase uploadImageUseCase;

    private ImageController imageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(getImageUseCase, uploadImageUseCase);
    }

    @Test
    @Order(1)
    @DisplayName("001 - Test uploadImage")
    void testUploadImage() throws IOException {
        int id = 1;
        byte[] imageBytes = "testImage".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE, imageBytes);
        Image uploadedImage = new Image(
                id,
                "test.jpg",
                "test.jpg"
        );

        when(uploadImageUseCase.execute(any(ImageRequest.class))).thenReturn(uploadedImage);

        ResponseEntity<Image> response = imageController.uploadImage(multipartFile, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(uploadedImage, response.getBody());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test getImage")
    void testGetImage() {
        String getPathName = "test_image.jpg";
        byte[] imageData = "Test image data".getBytes();

        when(getImageUseCase.execute(getPathName)).thenReturn(imageData);

        ResponseEntity<byte[]> responseEntity = imageController.getImage(getPathName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.IMAGE_JPEG, responseEntity.getHeaders().getContentType());
        assertEquals(imageData, responseEntity.getBody());

        verify(getImageUseCase).execute(getPathName);
    }
}