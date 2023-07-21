package com.music.musicStreamer.api.v1.models.dtos;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("TestPlaylistDTO Test")
public class TestPlaylistDTO {
    @Test
    @Order(1)
    @DisplayName("001 - Test Dto")
    public void testPlaylist(){
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(1);
        playlistDTO.setName("My Playlist");
        playlistDTO.setUserId(1);

        PlaylistDTO playlistDTO1 = new PlaylistDTO();
        playlistDTO1.setId(playlistDTO.getId());
        playlistDTO1.setName(playlistDTO.getName());
        playlistDTO1.setUserId(playlistDTO.getUserId());

        assertEquals(playlistDTO1.getId(), playlistDTO.getId());
        assertEquals(playlistDTO1.getName(), playlistDTO.getName());
        assertEquals(playlistDTO1.getUserId(), playlistDTO.getUserId());
    }
}