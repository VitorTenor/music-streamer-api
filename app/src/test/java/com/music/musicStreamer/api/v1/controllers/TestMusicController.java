package com.music.musicStreamer.api.v1.controllers;

import com.music.musicStreamer.api.v1.models.dtos.AddMusicDTO;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.music.MusicDownload;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.usecases.music.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("MusicController Class Test")
public class TestMusicController {
    @Mock
    private PlayMusicByIdUseCase playMusicByIdUseCase;
    @Mock
    private GetMusicByIdUseCase getMusicByIdUseCase;
    @Mock
    private UploadMusicUseCase uploadMusicUseCase;
    @Mock
    private DownloadByIdMusicUseCase downloadByIdMusicUseCase;
    @Mock
    private DeleteMusicByIdUseCase deleteMusicByIdUseCase;
    @Mock
    private GetAllMusicsUseCase getAllMusicsUseCase;

    private MusicController musicController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        musicController = new MusicController(
                playMusicByIdUseCase,
                getMusicByIdUseCase,
                uploadMusicUseCase,
                downloadByIdMusicUseCase,
                deleteMusicByIdUseCase,
                getAllMusicsUseCase
        );
    }

    @Test
    @Order(1)
    @DisplayName("001 - Test uploadMusic")
    public void testUploadMusic() throws Exception {
        AddMusicDTO addMusicDTO = new AddMusicDTO();
        addMusicDTO.setMusic(mock(MultipartFile.class));
        addMusicDTO.setName("Music Title");
        addMusicDTO.setArtist("Artist");
        addMusicDTO.setAlbum("Album");
        Music uploadedMusic = new Music();
        uploadedMusic.setId(1);
        uploadedMusic.setAlbum("Album");
        uploadedMusic.setArtist("Artist");
        uploadedMusic.setPathName("audio/mpeg");
        uploadedMusic.setName("Music Title");

        when(uploadMusicUseCase.execute(any())).thenReturn(uploadedMusic);

        ResponseEntity<Music> response = musicController.uploadMusic(addMusicDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(uploadedMusic, response.getBody());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test downloadMusic")
    public void testGetMusics() {
        Music music1 = new Music();
        music1.setId(1);
        music1.setAlbum("Album");
        music1.setArtist("Artist");
        music1.setPathName("audio/mpeg");
        music1.setName("Music Title");

        List<Music> allMusics = Arrays.asList(
                music1,
                music1
        );

        when(getAllMusicsUseCase.execute()).thenReturn(allMusics);

        ResponseEntity<Object> response = musicController.getMusics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allMusics, response.getBody());
    }

    @Test
    @Order(3)
    @DisplayName("003 - Test getMusic")
    public void testGetMusic() {
        int musicId = 1;
        Music music1 = new Music();
        music1.setId(1);
        music1.setAlbum("Album");
        music1.setArtist("Artist");
        music1.setPathName("audio/mpeg");
        music1.setName("Music Title");

        when(getMusicByIdUseCase.execute(musicId)).thenReturn(music1);

        ResponseEntity<Music> response = musicController.getMusic(musicId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(music1, response.getBody());
    }

    @Test
    @Order(4)
    @DisplayName("004 - Test deleteMusic")
    public void testPlayMusicById() throws IOException {
        int musicId = 1;
        byte[] musicContent = {1, 2, 3, 4, 5};

        when(playMusicByIdUseCase.execute(musicId)).thenReturn(musicContent);

        ResponseEntity<Object> response = musicController.playMusicById(musicId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.parseMediaType("audio/mpeg"), response.getHeaders().getContentType());
        assertEquals(musicContent, response.getBody());
    }

    @Test
    @Order(5)
    @DisplayName("005 - Test downloadMusicById")
    public void testDownloadMusicById() throws Exception {
        int musicId = 1;
        String musicType = null;
        MusicDownload musicDownload = new MusicDownload(mock(InputStream.class), mock(File.class));

        when(downloadByIdMusicUseCase.execute(musicId)).thenReturn(musicDownload);

        ResponseEntity response = musicController.downloadMusicById(musicId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        assertEquals(musicDownload.getInputStream() + musicType, response.getBody());
        assertEquals("attachment;filename=null", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals((long) musicDownload.getFile().length(), response.getHeaders().getContentLength());
    }

    @Test
    @Order(6)
    @DisplayName("006 - Test deleteMusicById")
    public void deleteMusicById_ValidId_ReturnsDeletedMusic() {
        int musicId = 1;

        when(deleteMusicByIdUseCase.execute(musicId)).thenReturn(MusicMessages.MUSIC_DELETED.getMessage());

        ResponseEntity<Object> response = musicController.deleteMusicById(musicId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MusicMessages.MUSIC_DELETED.getMessage(), response.getBody());
    }
}
