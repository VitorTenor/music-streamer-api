package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.PlaylistModel;
import com.music.musicStreamer.api.v1.models.PlaylistMusicModel;
import com.music.musicStreamer.api.v1.repositories.MusicRepository;
import com.music.musicStreamer.api.v1.repositories.PlaylistMusicRepository;
import com.music.musicStreamer.api.v1.repositories.PlaylistRepository;
import com.music.musicStreamer.api.v1.repositories.UserRepository;
import com.music.musicStreamer.core.utils.factories.PlaylistFactory;
import com.music.musicStreamer.core.utils.validators.PlaylistValidator;
import com.music.musicStreamer.core.utils.validators.UserValidator;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.entities.playlist.PlaylistMusic;
import com.music.musicStreamer.entities.playlist.PlaylistRequest;
import com.music.musicStreamer.enumerators.PlaylistMessages;
import com.music.musicStreamer.exceptions.PlaylistException;
import com.music.musicStreamer.gateways.PlaylistGateway;
import com.music.musicStreamer.usecases.playlistMusic.GetMusicByPlaylistIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaylistClient implements PlaylistGateway  {
    private final UserValidator userValidator;
    private final PlaylistFactory playlistFactory;
    private final PlaylistValidator playlistValidator;
    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;
    private final GetMusicByPlaylistIdUseCase getMusicByPlaylistIdUseCase;

    @Override
    @Transactional
    public Playlist createPlaylist(PlaylistRequest playlistRequest) {
        playlistValidator.validatePlaylist(playlistRequest);
        PlaylistModel playlistCreated = playlistFactory.createPlaylistModel(playlistRequest);

        save(playlistCreated);

        return playlistFactory.createPlaylist(playlistCreated);
    }

    @Override
    @Transactional
    public String addMusicToPlaylist(MusicPlaylistRequest musicPlaylistRequest) {
        playlistValidator.validateMusicPlaylist(musicPlaylistRequest);
        PlaylistMusicModel playlistMusicModel = playlistFactory.createPlaylistMusicModel(musicPlaylistRequest);

        playlistMusicRepository.save(playlistMusicModel);

        return PlaylistMessages.MUSIC_ADDED.getMessage();
    }

    @Override
    public PlaylistMusic getPlaylistById(int id) {
        PlaylistModel playlistModel = playlistRepository.findById(id).orElseThrow(() -> new PlaylistException(PlaylistMessages.NOT_FOUND));

        return new PlaylistMusic(playlistModel.getId(), playlistModel.getName(), playlistModel.getUserId(), getMusicByList(id));
    }

    @Override
    public List<Playlist> getPlaylistByUserId(int id) {
        userValidator.validateIfExistById(id);
        return playlistRepository.findAllByUserId(id).stream().map(playlistFactory::createPlaylist).toList();
    }

    private List<Music> getMusicByList(int playlistId) {
        return new ArrayList<>(getMusicByPlaylistIdUseCase.execute(playlistId));
    }

    private void save(PlaylistModel playlist) {
        playlistRepository.save(playlist);
    }
}

