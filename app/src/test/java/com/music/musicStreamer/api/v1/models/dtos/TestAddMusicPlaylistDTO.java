package com.music.musicStreamer.api.v1.models.dtos;

import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("AddMusicPlaylistDTO Test")
public class TestAddMusicPlaylistDTO {

    @Test
    @Order(1)
    @DisplayName("001 - Test toEntity method")
    public void testToEntity() {
        // Arrange
        int playlistId = 1;
        int musicId = 2;
        int userId = 3;

        AddMusicPlaylistDTO addMusicPlaylistDTO = new AddMusicPlaylistDTO();
        addMusicPlaylistDTO.setPlaylistId(playlistId);
        addMusicPlaylistDTO.setMusicId(musicId);
        addMusicPlaylistDTO.setUserId(userId);

        MusicPlaylistRequest expectedMusicPlaylistRequest = new MusicPlaylistRequest(playlistId, musicId, userId);

        MusicPlaylistRequest result = addMusicPlaylistDTO.toEntity();

        assertEquals(expectedMusicPlaylistRequest.getMusicId(), result.getMusicId());
        assertEquals(expectedMusicPlaylistRequest.getPlaylistId(), result.getPlaylistId());
        assertEquals(expectedMusicPlaylistRequest.getUserId(), result.getUserId());
    }

}
