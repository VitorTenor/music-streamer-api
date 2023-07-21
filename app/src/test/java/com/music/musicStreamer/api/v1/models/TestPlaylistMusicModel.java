package com.music.musicStreamer.api.v1.models;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test class PlaylistMusicModel")
public class TestPlaylistMusicModel {

    @Test
    @Order(1)
    @DisplayName("001 - Test create a PlaylistMusicModel object")
    public void testPlaylistMusic(){
        PlaylistMusicModel playlistMusicModel = new PlaylistMusicModel();
        playlistMusicModel.setId(1);
        playlistMusicModel.setPlaylistId(1);
        playlistMusicModel.setUserId(1);
        playlistMusicModel.setMusicId(1);
        playlistMusicModel.setCreated_at(new java.util.Date());
        playlistMusicModel.setUpdated_at(new java.util.Date());

        PlaylistMusicModel playlistMusicModel2 = new PlaylistMusicModel();
        playlistMusicModel2.setId(playlistMusicModel.getId());
        playlistMusicModel2.setPlaylistId(playlistMusicModel.getPlaylistId());
        playlistMusicModel2.setUserId(playlistMusicModel.getUserId());
        playlistMusicModel2.setMusicId(playlistMusicModel.getMusicId());
        playlistMusicModel2.setCreated_at(playlistMusicModel.getCreated_at());
        playlistMusicModel2.setUpdated_at(playlistMusicModel.getUpdated_at());

        assertEquals(playlistMusicModel.getId(), playlistMusicModel2.getId());
        assertEquals(playlistMusicModel.getPlaylistId(), playlistMusicModel2.getPlaylistId());
        assertEquals(playlistMusicModel.getUserId(), playlistMusicModel2.getUserId());
        assertEquals(playlistMusicModel.getMusicId(), playlistMusicModel2.getMusicId());
        assertEquals(playlistMusicModel.getCreated_at(), playlistMusicModel2.getCreated_at());
        assertEquals(playlistMusicModel.getUpdated_at(), playlistMusicModel2.getUpdated_at());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test create a PlaylistMusicModel constructor")
    public void testPlaylistMusicConstructor(){
        PlaylistMusicModel playlistMusicModel = new PlaylistMusicModel(1, 1, 1, new java.util.Date(), new java.util.Date());

        assertEquals(1, playlistMusicModel.getPlaylistId());
        assertEquals(1, playlistMusicModel.getUserId());
        assertEquals(1, playlistMusicModel.getMusicId());
        assertEquals(new java.util.Date(), playlistMusicModel.getCreated_at());
        assertEquals(new java.util.Date(), playlistMusicModel.getUpdated_at());
    }


}