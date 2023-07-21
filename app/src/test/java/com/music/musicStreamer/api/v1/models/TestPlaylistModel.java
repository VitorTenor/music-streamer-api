package com.music.musicStreamer.api.v1.models;

import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test class PlaylistModel")
public class TestPlaylistModel {

    @Test
    @Order(1)
    @DisplayName("001 - Test create a PlaylistModel object")
    public void testPlaylistModel(){
        PlaylistModel playlistModel = new PlaylistModel();
        playlistModel.setId(1);
        playlistModel.setName("name");
        playlistModel.setUserId(1);
        playlistModel.setCreated_at(new Date());
        playlistModel.setUpdated_at(new Date());

        PlaylistModel playlistModel2 = new PlaylistModel();
        playlistModel2.setId(playlistModel.getId());
        playlistModel2.setName(playlistModel.getName());
        playlistModel2.setUserId(playlistModel.getUserId());
        playlistModel2.setCreated_at(playlistModel.getCreated_at());
        playlistModel2.setUpdated_at(playlistModel.getUpdated_at());

        assertEquals(playlistModel.getId(), playlistModel2.getId());
        assertEquals(playlistModel.getName(), playlistModel2.getName());
        assertEquals(playlistModel.getUserId(), playlistModel2.getUserId());
        assertEquals(playlistModel.getCreated_at(), playlistModel2.getCreated_at());
        assertEquals(playlistModel.getUpdated_at(), playlistModel2.getUpdated_at());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test create a PlaylistModel constructor")
    public void testPlaylistModelConstructor(){
        PlaylistModel playlistModel = new PlaylistModel("name", 1, new Date(), new Date());

        assertEquals("name", playlistModel.getName());
        assertEquals(1, playlistModel.getUserId());
        assertEquals(new Date(), playlistModel.getCreated_at());
        assertEquals(new Date(), playlistModel.getUpdated_at());
    }

}