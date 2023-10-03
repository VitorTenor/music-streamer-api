package com.music.musicStreamer.api.v1.model;

import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test class MusicMode")
public class TestMusicModel {

    @Test
    @Order(1)
    @DisplayName("001 - Test create a MusicModel object")
    public void testMusicModel(){
        MusicModel musicModel = new MusicModel();
        musicModel.setAlbum("album");
        musicModel.setArtist("artist");
        musicModel.setCreated_at(new Date());
        musicModel.setGenre("genre");
        musicModel.setId(1);
        musicModel.setName("name");
        musicModel.setPathName("pathName");
        musicModel.setUpdated_at(new Date());

        MusicModel musicModel2 = new MusicModel();
        musicModel2.setAlbum(musicModel.getAlbum());
        musicModel2.setArtist(musicModel.getArtist());
        musicModel2.setCreated_at(musicModel.getCreated_at());
        musicModel2.setGenre(musicModel.getGenre());
        musicModel2.setId(musicModel.getId());
        musicModel2.setName(musicModel.getName());
        musicModel2.setPathName(musicModel.getPathName());
        musicModel2.setUpdated_at(musicModel.getUpdated_at());

        assertEquals(musicModel.getAlbum(), musicModel2.getAlbum());
        assertEquals(musicModel.getArtist(), musicModel2.getArtist());
        assertEquals(musicModel.getCreated_at(), musicModel2.getCreated_at());
        assertEquals(musicModel.getGenre(), musicModel2.getGenre());
        assertEquals(musicModel.getId(), musicModel2.getId());
        assertEquals(musicModel.getName(), musicModel2.getName());
        assertEquals(musicModel.getPathName(), musicModel2.getPathName());
        assertEquals(musicModel.getUpdated_at(), musicModel2.getUpdated_at());
    }
}