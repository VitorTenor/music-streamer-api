package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.database.model.MusicModel;
import com.music.musicStreamer.api.v1.database.model.PlaylistMusicModel;
import com.music.musicStreamer.api.v1.database.repository.MusicRepository;
import com.music.musicStreamer.api.v1.database.repository.PlaylistMusicRepository;
import com.music.musicStreamer.core.util.factory.PlaylistMusicFactory;
import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.entity.playlistmusic.PlaylistMusicEntity;
import com.music.musicStreamer.enums.PlaylistMessages;
import com.music.musicStreamer.gateway.PlaylistMusicGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
@RequiredArgsConstructor
public class PlaylistMusicClient implements PlaylistMusicGateway {

    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final PlaylistMusicFactory playlistMusicFactory;
    private final Logger LOGGER = Logger.getLogger(PlaylistMusicClient.class.getName());

    @Override
    @Transactional
    public String create(PlaylistMusicEntity entity) {
        info(this.getClass(), "Create playlist music");

        save(playlistMusicFactory.toModel(entity));
        info(this.getClass(), "Playlist music created");

        return PlaylistMessages.MUSIC_ADDED.getMessage();
    }

    @Override
    @Transactional
    public Boolean deleteMusicFromPlaylist(int id) {
        LOGGER.info("[PlaylistMusicClient] Delete music from playlist");

        List<PlaylistMusicModel> playlistMusic = playlistMusicRepository.findByMusicId(id);
        if (playlistMusic.isEmpty()) {
            LOGGER.info("[PlaylistMusicClient] Music not found in playlist, nothing to delete");
            return false;
        }
        for (PlaylistMusicModel playlistMusicModel : playlistMusic) {
            playlistMusicRepository.deleteById(playlistMusicModel.getId());
        }

        LOGGER.info("[PlaylistMusicClient] Music deleted from playlist");
        return true;
    }

    @Override
    public List<Music> getMusicByPlaylistId(int id) {
        LOGGER.info("[PlaylistMusicClient] Get music by playlist id");
        LOGGER.info("[PlaylistMusicClient] Playlist id: " + id);

        List<Music> musicList = new ArrayList<>();

        for (PlaylistMusicModel playlistMusicModel : playlistMusicRepository.findAllById(id)) {
            List<MusicModel> music = musicRepository.findAllById(playlistMusicModel.getMusicId());
            for (MusicModel musicModel : music) {
                musicList.add(playlistMusicFactory.createMusic(musicModel));
            }
        }

        LOGGER.info("[PlaylistMusicClient] Music list size: " + musicList.size());

        return musicList;
    }

    @Override
    public List<Long> getMusicIdByPlaylistId(final Long id) {
        return playlistMusicRepository.findMusicIdByPlaylistId(id.intValue());
    }

    public PlaylistMusicModel save(PlaylistMusicModel playlistMusicModel) {
        return playlistMusicRepository.save(playlistMusicModel);
    }
}
