package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.database.model.PlaylistMusicModel;
import com.music.musicStreamer.api.v1.database.repository.MusicRepository;
import com.music.musicStreamer.api.v1.database.repository.PlaylistMusicRepository;
import com.music.musicStreamer.core.util.factory.PlaylistMusicFactory;
import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.playlistmusic.PlaylistMusicEntity;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.enums.PlaylistMessages;
import com.music.musicStreamer.exception.MusicException;
import com.music.musicStreamer.gateway.PlaylistMusicGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
@RequiredArgsConstructor
public class PlaylistMusicClient implements PlaylistMusicGateway {
    /*
     * Factories
     */
    private final PlaylistMusicFactory playlistMusicFactory;
    /*
     * Repositories
     */
    private final MusicRepository musicRepository;
    private final PlaylistMusicRepository playlistMusicRepository;

    @Override
    @Transactional
    public String create(PlaylistMusicEntity entity) {
        info(this.getClass(), "Create playlist music");

        this.save(playlistMusicFactory.toModel(entity));

        info(this.getClass(), "Playlist music created");

        return PlaylistMessages.MUSIC_ADDED.getMessage();
    }

    @Override
    @Transactional
    public Boolean deleteByMusicId(final Long musicId) {
        info(this.getClass(), "Delete music from playlist");

        final var playlistMusic = playlistMusicRepository.findByMusicId(musicId.intValue());
        if (playlistMusic.isEmpty()) {
            info(this.getClass(), "Music not found in playlist, nothing to delete");
            return Boolean.FALSE;
        }
        playlistMusic.stream().parallel()
                .map(PlaylistMusicModel::getId)
                .forEach(playlistMusicRepository::deleteById);

        info(this.getClass(), "Music deleted from playlist");
        return Boolean.TRUE;
    }

    @Override
    public List<MusicEntity> getMusicByPlaylistId(int id) {
        info(this.getClass(), "Get music by playlist id");
        info(this.getClass(), "Playlist id: " + id);

        final var musicList = playlistMusicRepository.findAllById(id);

        return musicList.stream().parallel()
                .map(
                        playlistMusicModel -> {
                            final var music = musicRepository.findById(playlistMusicModel.getMusicId()).orElseThrow(
                                    () -> new MusicException(MusicMessages.NOT_FOUND)
                            );
                            return playlistMusicFactory.createMusic(music);
                        }
                ).toList();
    }

    @Override
    public List<Long> getMusicIdByPlaylistId(final Long id) {
        info(this.getClass(), "Get music id by playlist id");
        return playlistMusicRepository.findMusicIdByPlaylistId(id.intValue());
    }

    public void save(PlaylistMusicModel playlistMusicModel) {
        info(this.getClass(), "Save playlist music");
        playlistMusicRepository.save(playlistMusicModel);
    }
}
