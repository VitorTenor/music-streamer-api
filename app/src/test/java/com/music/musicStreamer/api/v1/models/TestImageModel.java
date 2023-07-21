package com.music.musicStreamer.api.v1.models;

import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test class ImageModel")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestImageModel {

    @Test
    @Order(1)
    @DisplayName("001 - Test create a ImageModel object")
    public void testCreateImageModel() {
        ImageModel imageModel = new ImageModel();
        imageModel.setCreated_at(new Date());
        imageModel.setUpdated_at(new Date());
        imageModel.setPathName("pathName");
        imageModel.setId(1);
        imageModel.setMusicId(1);

        ImageModel imageModel2 = new ImageModel();
        imageModel2.setCreated_at(imageModel.getCreated_at());
        imageModel2.setUpdated_at(imageModel.getUpdated_at());
        imageModel2.setPathName(imageModel.getPathName());
        imageModel2.setId(imageModel.getId());
        imageModel2.setMusicId(imageModel.getMusicId());

        assertEquals(imageModel.getCreated_at(), imageModel2.getCreated_at());
        assertEquals(imageModel.getUpdated_at(), imageModel2.getUpdated_at());
        assertEquals(imageModel.getPathName(), imageModel2.getPathName());
        assertEquals(imageModel.getId(), imageModel2.getId());
        assertEquals(imageModel.getMusicId(), imageModel2.getMusicId());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test create a ImageModel constructor")
    public void testCreateImageModelConstructor() {
        ImageModel imageModel = new ImageModel(1, "pathName", new Date(), new Date());

        assertNotEquals(1, imageModel.getMusicId());
        assertEquals("pathName", imageModel.getPathName());
        assertEquals(new Date(), imageModel.getCreated_at());
        assertEquals(new Date(), imageModel.getUpdated_at());
    }
}