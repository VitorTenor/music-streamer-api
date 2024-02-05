package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.database.model.PlaylistModel;
import com.music.musicStreamer.api.v1.database.repository.PlaylistRepository;
import com.music.musicStreamer.core.util.factory.PlaylistFactory;
import com.music.musicStreamer.core.util.factory.PlaylistMusicFactory;
import com.music.musicStreamer.entity.playlist.CreatePlaylistEntity;
import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import com.music.musicStreamer.entity.playlist.PlaylistWithMusicEntity;
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
    public PlaylistWithMusicEntity getById(final int id) {
        info(this.getClass(), "Get playlist by id");
        final var playlistModel = playlistRepository.findById(id).orElseThrow(() -> new PlaylistException(PlaylistMessages.NOT_FOUND));

        info(this.getClass(), "Playlist found");
        info(this.getClass(), "Playlist id: " + playlistModel.getId());

        final var musics = playlistMusicGateway.getMusicByPlaylistId(playlistModel.getId());

        return playlistMusicFactory.toEntity(playlistModel, musics);
    }

    @Override
    public List<PlaylistEntity> getByUserId(final int id) {
        info(this.getClass(), "Get playlist by user id");
        info(this.getClass(), "User id: " + id);


        return playlistRepository.findAllByUserId(id).stream().map(playlistFactory::toEntity).toList();
    }


    private PlaylistModel save(PlaylistModel playlist) {
        info(this.getClass(), "Save playlist => " + playlist.getName());
        return playlistRepository.save(playlist);
    }
}

