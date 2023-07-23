package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.PlaylistModel;
import com.music.musicStreamer.api.v1.repositories.PlaylistMusicRepository;
import com.music.musicStreamer.api.v1.repositories.PlaylistRepository;
import com.music.musicStreamer.core.utils.factories.PlaylistFactory;
import com.music.musicStreamer.core.utils.validators.PlaylistValidator;
import com.music.musicStreamer.core.utils.validators.UserValidator;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.entities.playlist.PlaylistMusic;
import com.music.musicStreamer.entities.playlist.PlaylistRequest;
import com.music.musicStreamer.enums.PlaylistMessages;
import com.music.musicStreamer.usecases.playlistMusic.GetMusicByPlaylistIdUseCase;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("PlaylistClient Class Test")
public class TestPlaylistClient {
    private PlaylistClient playlistClient;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PlaylistFactory playlistFactory;

    @Mock
    private PlaylistValidator playlistValidator;

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private PlaylistMusicRepository playlistMusicRepository;

    @Mock
    private GetMusicByPlaylistIdUseCase getMusicByPlaylistIdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        playlistClient = new PlaylistClient(
                userValidator,
                playlistFactory,
                playlistValidator,
                playlistRepository,
                playlistMusicRepository,
                getMusicByPlaylistIdUseCase
        );
    }

    @Test
    @Order(1)
    @DisplayName("001 - Test method createPlaylist with invalid playlist request")
    public void testCreatePlaylist() {
        PlaylistRequest playlistRequest = new PlaylistRequest(
                "playlistName",
                1
        );
        PlaylistModel playlistModel = new PlaylistModel(
                playlistRequest.getName(),
                playlistRequest.getUserId(),
                new Date(),
                new Date()
        );
        Playlist expectedPlaylist = new Playlist(
                playlistModel.getName(),
                playlistModel.getUserId(),
                playlistModel.getId()
        );

        when(playlistFactory.createPlaylistModel(playlistRequest)).thenReturn(playlistModel);
        when(playlistFactory.createPlaylist(playlistModel)).thenReturn(expectedPlaylist);

        Playlist createdPlaylist = playlistClient.createPlaylist(playlistRequest);

        assertThat(createdPlaylist).isEqualTo(expectedPlaylist);
        verify(playlistValidator).validatePlaylist(playlistRequest);
        verify(playlistRepository).save(playlistModel);
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test method createPlaylist with valid playlist request")
    public void testAddMusicToPlaylist() {
        MusicPlaylistRequest musicPlaylistRequest = new MusicPlaylistRequest(
                1,
                1,
                1
        );

        String resultMessage = playlistClient.addMusicToPlaylist(musicPlaylistRequest);

        assertThat(resultMessage).isEqualTo(PlaylistMessages.MUSIC_ADDED.getMessage());
        verify(playlistValidator).validateMusicPlaylist(musicPlaylistRequest);
    }

    @Test
    @Order(3)
    @DisplayName("003 - Test method deletePlaylist with invalid playlist id")
    public void testGetPlaylistById() {
        int playlistId = 1;
        PlaylistModel playlistModel = new PlaylistModel(/* initialize with expected data */);
        List<Music> expectedMusicList = new ArrayList<>();
        expectedMusicList.add(new Music(/* initialize with expected data */));
        expectedMusicList.add(new Music(/* initialize with expected data */));

        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlistModel));
        when(getMusicByPlaylistIdUseCase.execute(playlistId)).thenReturn(expectedMusicList);

        PlaylistMusic playlistMusic = playlistClient.getPlaylistById(playlistId);

        assertThat(playlistMusic.getId()).isEqualTo(playlistModel.getId());
        assertThat(playlistMusic.getName()).isEqualTo(playlistModel.getName());
        assertThat(playlistMusic.getUserId()).isEqualTo(playlistModel.getUserId());
        verify(playlistRepository).findById(playlistId);
        verify(getMusicByPlaylistIdUseCase).execute(playlistId);
    }

    @Test
    @Order(4)
    @DisplayName("004 - Test method deletePlaylist with invalid playlist id")
    public void testGetPlaylistByUserId() {
        int userId = 1;
        PlaylistRequest playlistRequest = new PlaylistRequest(
                "playlistName",
                1
        );
        PlaylistModel playlistModel = new PlaylistModel(
                playlistRequest.getName(),
                playlistRequest.getUserId(),
                new Date(),
                new Date()
        );
        Playlist playlist = new Playlist(
                playlistModel.getName(),
                playlistModel.getUserId(),
                playlistModel.getId()
        );
        List<PlaylistModel> playlistModels = new ArrayList<>();
        playlistModels.add(playlistModel);
        playlistModels.add(playlistModel);
        List<Playlist> expectedPlaylists = new ArrayList<>();
        expectedPlaylists.add(playlist);
        expectedPlaylists.add(playlist);

        when(playlistRepository.findAllByUserId(userId)).thenReturn(playlistModels);
        when(playlistFactory.createPlaylist(any(PlaylistModel.class))).thenReturn(expectedPlaylists.get(0), expectedPlaylists.get(1));

        List<Playlist> playlists = playlistClient.getPlaylistByUserId(userId);

        assertThat(playlists).isEqualTo(expectedPlaylists);
        verify(userValidator).validateIfExistById(userId);
        verify(playlistRepository).findAllByUserId(userId);
        verify(playlistFactory, times(2)).createPlaylist(any(PlaylistModel.class));
    }
}
