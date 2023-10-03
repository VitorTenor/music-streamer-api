package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.model.dtos.AddMusicPlaylistDTO;
import com.music.musicStreamer.api.v1.model.dtos.CreatePlaylistDTO;
import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.entity.playlist.Playlist;
import com.music.musicStreamer.entity.playlist.PlaylistMusic;
import com.music.musicStreamer.usecase.playlist.AddMusicPlaylistUseCase;
import com.music.musicStreamer.usecase.playlist.CreatePlaylistUseCase;
import com.music.musicStreamer.usecase.playlist.GetPlaylistByIdUseCase;
import com.music.musicStreamer.usecase.playlist.GetPlaylistByUserIdUseCase;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("PlaylistController Class Test")
public class TestPlaylistController {
    @Mock
    private CreatePlaylistUseCase createPlaylistUseCase;
    @Mock
    private GetPlaylistByIdUseCase getPlaylistByIdUseCase;
    @Mock
    private AddMusicPlaylistUseCase addMusicPlaylistUseCase;
    @Mock
    private GetPlaylistByUserIdUseCase getPlaylistByUserIdUseCase;

    private PlaylistController playlistController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        playlistController = new PlaylistController(
                createPlaylistUseCase,
                getPlaylistByIdUseCase,
                addMusicPlaylistUseCase,
                getPlaylistByUserIdUseCase
        );
    }

    @Test
    @Order(1)
    @DisplayName("001 - Test createPlaylist")
    public void createPlaylist() throws Exception {
        CreatePlaylistDTO createPlaylistDTO = new CreatePlaylistDTO();
        createPlaylistDTO.setUserId(1);
        createPlaylistDTO.setName("My Playlist");
        Playlist createdPlaylist = new Playlist(
                "My Playlist",
                1,
                1
        );


        when(createPlaylistUseCase.execute(any())).thenReturn(createdPlaylist);

        ResponseEntity<Playlist> response = playlistController.createPlaylist(createPlaylistDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdPlaylist, response.getBody());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test getPlaylistById")
    public void addSongToPlaylist() {
        AddMusicPlaylistDTO addMusicPlaylistDTO = new AddMusicPlaylistDTO();
        addMusicPlaylistDTO.setPlaylistId(1);
        addMusicPlaylistDTO.setMusicId(1);
        String successMessage = "Song added to playlist successfully";

        when(addMusicPlaylistUseCase.execute(any())).thenReturn(successMessage);

        ResponseEntity<String> response = playlistController.addSongToPlaylist(addMusicPlaylistDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successMessage, response.getBody());
    }

    @Test
    @Order(3)
    @DisplayName("003 - Test getPlaylistById")
    public void getPlaylistById() {
        int playlistId = 1;
        PlaylistMusic playlist = new PlaylistMusic(
                1,
                "My Playlist",
                1,
                List.of(mock(Music.class))
        );
        when(getPlaylistByIdUseCase.execute(playlistId)).thenReturn(playlist);

        ResponseEntity<Object> response = playlistController.getPlaylistById(playlistId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(playlist, response.getBody());
    }

    @Test
    @Order(4)
    @DisplayName("004 - Test getAllPlaylistsByUserId")
    public void getAllPlaylistsByUserId() {
        int userId = 1;

        List<Playlist> playlistList = List.of(new Playlist(
                "My Playlist",
                1,
                1
        ));

        when(getPlaylistByUserIdUseCase.execute(userId)).thenReturn(playlistList);

        ResponseEntity<Object> response = playlistController.getAllPlaylistsByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(playlistList, response.getBody());
    }
}
