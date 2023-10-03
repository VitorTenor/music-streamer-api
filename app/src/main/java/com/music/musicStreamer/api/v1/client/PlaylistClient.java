package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.model.PlaylistModel;
import com.music.musicStreamer.api.v1.model.PlaylistMusicModel;
import com.music.musicStreamer.api.v1.repository.PlaylistMusicRepository;
import com.music.musicStreamer.api.v1.repository.PlaylistRepository;
import com.music.musicStreamer.core.util.factory.PlaylistFactory;
import com.music.musicStreamer.core.util.validator.PlaylistValidator;
import com.music.musicStreamer.core.util.validator.UserValidator;
import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entity.playlist.Playlist;
import com.music.musicStreamer.entity.playlist.PlaylistMusic;
import com.music.musicStreamer.entity.playlist.PlaylistRequest;
import com.music.musicStreamer.enums.PlaylistMessages;
import com.music.musicStreamer.exception.PlaylistException;
import com.music.musicStreamer.gateway.PlaylistGateway;
import com.music.musicStreamer.usecase.playlistMusic.GetMusicByPlaylistIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaylistClient implements PlaylistGateway {
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

