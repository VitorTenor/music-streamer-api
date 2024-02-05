package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.database.model.PlaylistModel;
import com.music.musicStreamer.api.v1.database.model.PlaylistMusicModel;
import com.music.musicStreamer.api.v1.database.repository.PlaylistMusicRepository;
import com.music.musicStreamer.api.v1.database.repository.PlaylistRepository;
import com.music.musicStreamer.core.util.factory.PlaylistFactory;
import com.music.musicStreamer.core.util.factory.PlaylistMusicFactory;
import com.music.musicStreamer.core.util.validator.PlaylistValidator;
import com.music.musicStreamer.core.util.validator.UserValidator;
import com.music.musicStreamer.entity.playlist.CreatePlaylistEntity;
import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import com.music.musicStreamer.entity.playlist.PlaylistMusicEntity;
import com.music.musicStreamer.enums.PlaylistMessages;
import com.music.musicStreamer.exception.PlaylistException;
import com.music.musicStreamer.gateway.PlaylistGateway;
import com.music.musicStreamer.gateway.PlaylistMusicGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
@RequiredArgsConstructor
public class PlaylistClient implements PlaylistGateway {
    /*
     * Clients
     */
    private final PlaylistMusicGateway playlistMusicGateway;

    /*
     * Factories
     */
    private final PlaylistFactory playlistFactory;
    private final PlaylistMusicFactory playlistMusicFactory;

    private final PlaylistRepository playlistRepository;
    // TODO: REMOVE
    private final UserValidator userValidator;
    // TODO: REMOVE
    private final PlaylistValidator playlistValidator;

    // TODO: REMOVE
    private final PlaylistMusicRepository playlistMusicRepository;


    private final Logger LOGGER = Logger.getLogger(PlaylistClient.class.getName());

    @Override
    @Transactional
    public PlaylistEntity create(CreatePlaylistEntity entity) {
        info(this.getClass(), "Create playlist");
        info(this.getClass(), "Playlist name: " + entity.name());

        final var playlist = playlistFactory.toModel(entity);
        final var playlistModel = save(playlist);

        final var musicIds = playlistMusicGateway.getMusicIdByPlaylistId((long) playlistModel.getId());

        info(this.getClass(), "Playlist created");
        info(this.getClass(), "Playlist id: " + playlistModel.getId());

        return playlistFactory.toEntity(playlistModel, musicIds);
    }

    @Override
    @Transactional
    public String addMusicToPlaylist(MusicPlaylistRequest musicPlaylistRequest) {
        LOGGER.info("[PlaylistClient] Add song to playlist");
        LOGGER.info("[PlaylistClient] Playlist id: " + musicPlaylistRequest.getPlaylistId());
        LOGGER.info("[PlaylistClient] Music id: " + musicPlaylistRequest.getMusicId());

        playlistValidator.validateMusicPlaylist(musicPlaylistRequest);
        PlaylistMusicModel playlistMusicModel = playlistFactory.createPlaylistMusicModel(musicPlaylistRequest);
        playlistMusicRepository.save(playlistMusicModel);
        LOGGER.info("[PlaylistClient] Song added to playlist in database");

        return PlaylistMessages.MUSIC_ADDED.getMessage();
    }

    @Override
    public PlaylistMusicEntity getPlaylistById(final int id) {
        info(this.getClass(), "Get playlist by id");
        final var playlistModel = playlistRepository.findById(id).orElseThrow(() -> new PlaylistException(PlaylistMessages.NOT_FOUND));

        info(this.getClass(), "Playlist found");
        info(this.getClass(), "Playlist id: " + playlistModel.getId());

        final var musics = playlistMusicGateway.getMusicByPlaylistId(playlistModel.getId());

        return playlistMusicFactory.toEntity(playlistModel, musics);
    }

    @Override
    public List<PlaylistEntity> getPlaylistByUserId(int id) {
        LOGGER.info("[PlaylistClient] Get playlist by user id");
        LOGGER.info("[PlaylistClient] User id: " + id);

        userValidator.validateIfExistById(id);

        LOGGER.info("[PlaylistClient] User found");

        return playlistRepository.findAllByUserId(id).stream().map(playlistFactory::createPlaylist).toList();
    }

    private PlaylistModel save(PlaylistModel playlist) {
        return playlistRepository.save(playlist);
    }
}

