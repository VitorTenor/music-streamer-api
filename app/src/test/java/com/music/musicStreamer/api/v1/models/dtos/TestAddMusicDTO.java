package com.music.musicStreamer.api.v1.models.dtos;

import com.music.musicStreamer.entities.music.MusicRequest;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("AddMusicDTO Test")
public class TestAddMusicDTO {

    @Test
    @Order(1)
    @DisplayName("001 - Test toEntity method")
    public void toEntity_ValidAddMusicDTO_ReturnsMusicRequest() throws Exception {
        String name = "Music Name";
        String artist = "Artist Name";
        String album = "Album Name";
        String genre = "Genre";
        byte[] musicContent = "Music Content".getBytes(StandardCharsets.UTF_8);
        MultipartFile musicFile = new MockMultipartFile("music.mp3", musicContent);

        AddMusicDTO addMusicDTO = new AddMusicDTO();
        addMusicDTO.setName(name);
        addMusicDTO.setArtist(artist);
        addMusicDTO.setAlbum(album);
        addMusicDTO.setGenre(genre);
        addMusicDTO.setMusic(musicFile);

        byte[] expectedMusicContent = musicContent.clone();

        MusicRequest expectedMusicRequest = new MusicRequest(name, artist, album, genre, expectedMusicContent);

        MusicRequest result = addMusicDTO.toEntity();

        assertEquals(expectedMusicRequest.getName(), result.getName());
        assertEquals(expectedMusicRequest.getArtist(), result.getArtist());
        assertEquals(expectedMusicRequest.getAlbum(), result.getAlbum());
        assertEquals(expectedMusicRequest.getGenre(), result.getGenre());
    }

}
