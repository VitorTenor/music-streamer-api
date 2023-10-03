package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.model.MusicModel;
import com.music.musicStreamer.api.v1.model.PlaylistMusicModel;
import com.music.musicStreamer.api.v1.repository.MusicRepository;
import com.music.musicStreamer.api.v1.repository.PlaylistMusicRepository;
import com.music.musicStreamer.core.util.factory.PlaylistMusicFactory;
import com.music.musicStreamer.entity.music.Music;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("PlaylistMusicClient Class Test")
public class TestPlaylistMusicClient {
    private PlaylistMusicClient playlistMusicClient;

    @Mock
    private PlaylistMusicRepository playlistMusicRepository;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private PlaylistMusicFactory playlistMusicFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        playlistMusicClient = new PlaylistMusicClient(playlistMusicRepository, musicRepository, playlistMusicFactory);
    }

    @Test
    @Order(1)
    @DisplayName("001 -Test method deleteMusicFromPlaylist with invalid music id")
    public void testDeleteMusicFromPlaylist() {
        int musicId = 1;
        List<PlaylistMusicModel> playlistMusicModels = new ArrayList<>();
        playlistMusicModels.add(new PlaylistMusicModel(/* initialize with expected data */));
        playlistMusicModels.add(new PlaylistMusicModel(/* initialize with expected data */));

        when(playlistMusicRepository.findByMusicId(musicId)).thenReturn(playlistMusicModels);

        boolean result = playlistMusicClient.deleteMusicFromPlaylist(musicId);

        assertThat(result).isTrue();
        verify(playlistMusicRepository, times(2)).deleteById(anyInt());
    }

    @Test
    @Order(2)
    @DisplayName("002 -Test method getMusicByPlaylistId with invalid playlist id")
    public void testGetMusicByPlaylistId() {
        int playlistId = 1;
        List<PlaylistMusicModel> playlistMusicModels = new ArrayList<>();
        playlistMusicModels.add(new PlaylistMusicModel(/* initialize with expected data */));
        playlistMusicModels.add(new PlaylistMusicModel(/* initialize with expected data */));
        List<MusicModel> musicModels = new ArrayList<>();
        musicModels.add(new MusicModel(/* initialize with expected data */));
        musicModels.add(new MusicModel(/* initialize with expected data */));
        List<Music> expectedMusicList = new ArrayList<>();
        expectedMusicList.add(new Music(/* initialize with expected data */));
        expectedMusicList.add(new Music(/* initialize with expected data */));

        when(playlistMusicRepository.findAllById(playlistId)).thenReturn(playlistMusicModels);
        when(musicRepository.findAllById(anyInt())).thenReturn(musicModels);
        when(playlistMusicFactory.createMusic(any(MusicModel.class))).thenReturn(expectedMusicList.get(0), expectedMusicList.get(1));

        playlistMusicClient.getMusicByPlaylistId(playlistId);

        verify(playlistMusicRepository).findAllById(playlistId);
        verify(musicRepository, times(2)).findAllById(anyInt());
    }
}
