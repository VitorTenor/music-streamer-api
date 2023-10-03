package com.music.musicStreamer.api.v1.client;


import com.music.musicStreamer.api.v1.model.ImageModel;
import com.music.musicStreamer.api.v1.repository.ImageRepository;
import com.music.musicStreamer.core.util.GenerateName;
import com.music.musicStreamer.core.storage.impl.ImageFiles;
import com.music.musicStreamer.core.util.factory.ImageFactory;
import com.music.musicStreamer.core.util.validator.ImageValidator;
import com.music.musicStreamer.entity.image.Image;
import com.music.musicStreamer.entity.image.ImageRequest;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DisplayName("ImageClient Class Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestImageClient {
    @Mock
    private GenerateName generateName;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageValidator imageValidator;

    @Mock
    private ImageFactory imageFactory;

    @Mock
    private ImageFiles imageFiles;

    private ImageClient imageClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageClient = new ImageClient(imageFiles, generateName, imageFactory, imageValidator, imageRepository);
    }

    @Test
    @Order(1)
    @DisplayName("001 - Test saveImage")
    void testSaveImage() {
        ImageRequest imageRequest = new ImageRequest(new byte[]{}, 1);
        String newFileName = "newFileName";
        ImageModel imageModel = new ImageModel();

        when(generateName.randomName()).thenReturn(newFileName);
        when(imageFactory.createImageModel(imageRequest, newFileName)).thenReturn(imageModel);
        when(imageRepository.save(imageModel)).thenReturn(imageModel);

        imageClient.saveImage(imageRequest);

        verify(imageValidator).validateImage(imageRequest);
        verify(imageFiles).saveInFiles(imageRequest, newFileName);
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test deleteImageByMusicId")
    void testDeleteImageByMusicId() {
        int musicId = 1;
        ImageModel imageModel = new ImageModel();

        when(imageRepository.findByMusicId(musicId)).thenReturn(imageModel);

        boolean result = imageClient.deleteImageByMusicId(musicId);

        assertTrue(result);
        verify(imageValidator).validateIfImageIsNotNull(imageModel);
        verify(imageFiles).deleteInFiles(imageModel.getPathName());
        verify(imageRepository).deleteById(imageModel.getId());
    }

    @Test
    @Order(3)
    @DisplayName("003 - Test getImageByFileName")
    void testGetImageByFileName() {
        String fileName = "test.jpg";
        byte[] imageData = new byte[]{};

        when(imageFiles.getBytesInFiles(fileName)).thenReturn(imageData);

        byte[] result = imageClient.getImageByFileName(fileName);

        assertArrayEquals(imageData, result);
    }

    @Test
    @Order(4)
    @DisplayName("004 - Test getImageById")
    void testGetImageById() {
        int imageId = 1;
        ImageModel imageModel = new ImageModel();
        Image image = new Image(
                imageModel.getId(),
                imageModel.getPathName(),
                imageModel.getPathName()
        );

        when(imageRepository.findByMusicId(imageId)).thenReturn(imageModel);
        when(imageFactory.createImage(imageModel)).thenReturn(image);

        Image result = imageClient.getImageById(imageId);

        assertEquals(image, result);
        verify(imageValidator).validateIfImageIsNotNull(imageModel);
    }

    @Test
    @Order(5)
    @DisplayName("005 - Test getImageByMusicId")
    void testGetImageByMusicId() {
        int musicId = 1;
        ImageModel imageModel = new ImageModel();
        Image image = new Image(
                imageModel.getId(),
                imageModel.getPathName(),
                imageModel.getPathName()
        );

        when(imageRepository.findByMusicId(musicId)).thenReturn(imageModel);
        when(imageFactory.createImage(imageModel)).thenReturn(image);

        Image result = imageClient.getImageByMusicId(musicId);

        assertEquals(image, result);
        verify(imageValidator).validateIfImageIsNotNull(imageModel);
    }
}