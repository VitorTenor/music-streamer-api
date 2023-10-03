package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.model.MusicModel;
import com.music.musicStreamer.api.v1.repository.MusicRepository;
import com.music.musicStreamer.core.util.GenerateName;
import com.music.musicStreamer.core.storage.impl.MusicFiles;
import com.music.musicStreamer.core.util.factory.MusicFactory;
import com.music.musicStreamer.core.util.validator.MusicValidator;
import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.entity.music.MusicRequest;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.exception.MusicException;
import com.music.musicStreamer.usecase.image.DeleteImageByMusicIdUseCase;
import com.music.musicStreamer.usecase.playlistMusic.DeleteMusicFromPlaylistUseCase;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("MusicClient Class Test")
public class TestMusicClient{
    private MusicClient musicClient;

    @Mock
    private MusicFiles musicFiles;

    @Mock
    private GenerateName generateName;

    @Mock
    private MusicFactory musicFactory;

    @Mock
    private MusicValidator musicValidator;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private DeleteImageByMusicIdUseCase deleteImageByMusicIdUseCase;

    @Mock
    private DeleteMusicFromPlaylistUseCase deleteMusicFromPlaylistUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        musicClient = new MusicClient(
                musicFiles,
                generateName,
                musicFactory,
                musicValidator,
                musicRepository,
                deleteImageByMusicIdUseCase,
                deleteMusicFromPlaylistUseCase
        );
    }

    @Test
    @Order(1)
    @DisplayName("001 - Test save music with invalid music request")
    public void testSaveMusic() {
        MusicRequest musicRequest = new MusicRequest(
                "music_name",
                "music_artist",
                "music_album",
                "music_genre",
                new byte[0]
        );
        String newFileName = "generated_file_name";
        MusicModel musicModel = new MusicModel(/* initialize with expected data */);
        Music expectedMusic = new Music(/* initialize with expected data */);

        when(generateName.randomName()).thenReturn(newFileName);
        when(musicFactory.createModel(musicRequest, newFileName)).thenReturn(musicModel);
        when(musicFactory.createMusic(musicModel)).thenReturn(expectedMusic);

        musicClient.saveMusic(musicRequest);

        verify(musicValidator).validateMusic(musicRequest);
        verify(musicFiles).saveInFiles(musicRequest, newFileName);
        verify(musicRepository).save(musicModel);
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test save music with invalid music request")
    public void testGetAllMusics() {
        List<Music> expectedMusics = new ArrayList<>();
        expectedMusics.add(new Music(/* initialize with expected data */));
        expectedMusics.add(new Music(/* initialize with expected data */));

        when(musicFiles.getAllInFiles()).thenReturn(expectedMusics);

        List<Music> musics = musicClient.getAllMusics();

        assertThat(musics).isEqualTo(expectedMusics);
        verify(musicFiles).getAllInFiles();
    }

    @Test
    @Order(3)
    @DisplayName("003 - Test get music by id with invalid music id")
    public void testPlayMusic() {
        int musicId = 1;
        MusicModel musicModel = new MusicModel();
        InputStream expectedStream = mock(InputStream.class);

        when(musicRepository.findById(musicId)).thenReturn(Optional.of(musicModel));
        when(musicFiles.getInFilesStream(anyString())).thenReturn(expectedStream);
        doNothing().when(musicValidator).validateIfMusicIsNotNull(any());

        musicClient.playMusic(musicId);

        verify(musicRepository).findById(musicId);
        verify(musicValidator).validateIfMusicIsNotNull(musicModel);
    }

    @Test
    @Order(4)
    @DisplayName("004 - Test get music by id with invalid music id")
    public void testDownloadMusic() {
        int musicId = 1;
        MusicModel musicModel = new MusicModel(/* initialize with expected data */);
        InputStream expectedResource = mock(InputStream.class);
        File expectedFile = mock(File.class);

        when(musicRepository.findById(musicId)).thenReturn(Optional.of(musicModel));
        when(musicFiles.getInFilesStream(anyString())).thenReturn(expectedResource);
        when(musicFiles.getFile(anyString())).thenReturn(expectedFile);
        doNothing().when(musicValidator).validateIfMusicIsNotNull(any());

        musicClient.downloadMusic(musicId);

        verify(musicRepository).findById(musicId);
        verify(musicValidator).validateIfMusicIsNotNull(musicModel);
    }


    @Test
    @Order(5)
    @DisplayName("005 - Test get music by id with invalid music id")
    public void testGetMusicById() {
        int musicId = 1;
        MusicModel musicModel = new MusicModel(/* initialize with expected data */);
        Music expectedMusic = new Music(/* initialize with expected data */);

        when(musicRepository.findById(musicId)).thenReturn(Optional.of(musicModel));
        when(musicFactory.createMusic(musicModel)).thenReturn(expectedMusic);

        Music music = musicClient.getMusicById(musicId);

        assertThat(music).isEqualTo(expectedMusic);
        verify(musicRepository).findById(musicId);
        verify(musicFactory).createMusic(musicModel);
    }

    @Test
    @Order(6)
    @DisplayName("006 - Test get music by id with invalid music id")
    public void testDeleteMusicById() {
        int musicId = 1;
        MusicModel musicModel = new MusicModel(/* initialize with expected data */);
        String successMessage = MusicMessages.MUSIC_DELETED.getMessage();

        when(musicRepository.findById(musicId)).thenReturn(Optional.of(musicModel));

        String message = musicClient.deleteMusicById(musicId);

        assertThat(message).isEqualTo(successMessage);
        verify(musicRepository).findById(musicId);
        verify(musicRepository).deleteById(musicId);
        verify(deleteImageByMusicIdUseCase).execute(musicId);
        verify(deleteMusicFromPlaylistUseCase).execute(musicId);
        verify(musicFiles).deleteInFiles(musicModel.getPathName());
    }

    @Test
    @Order(7)
    @DisplayName("007 - Test get music by id with invalid music id")
    public void testDeleteMusicByIdWithInvalid() {
        int musicId = 1;

        when(musicRepository.findById(musicId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> musicClient.deleteMusicById(musicId))
                .isInstanceOf(MusicException.class)
                .hasMessage(MusicMessages.NOT_FOUND.getMessage());
        verify(musicRepository).findById(musicId);
        verify(musicRepository, never()).deleteById(anyInt());
        verify(deleteImageByMusicIdUseCase, never()).execute(anyInt());
        verify(deleteMusicFromPlaylistUseCase, never()).execute(anyInt());
        verify(musicFiles, never()).deleteInFiles(anyString());
    }
}
