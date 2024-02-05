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

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
@RequiredArgsConstructor
public class PlaylistMusicClient implements PlaylistMusicGateway {

    private final PlaylistMusicRepository playlistMusicRepository;
    /*
     * Factories
     */
    private final PlaylistMusicFactory playlistMusicFactory;

    private final MusicRepository musicRepository;

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
    public Boolean delete(int id) {
        info(this.getClass(), "Delete music from playlist");

        var playlistMusic = playlistMusicRepository.findByMusicId(id);
        if (playlistMusic.isEmpty()) {
            info(this.getClass(), "Music not found in playlist, nothing to delete");
            return Boolean.FALSE;
        }
        for (PlaylistMusicModel playlistMusicModel : playlistMusic) {
            playlistMusicRepository.deleteById(playlistMusicModel.getId());
        }

        info(this.getClass(), "Music deleted from playlist");
        return Boolean.TRUE;
    }

    @Override
    public List<Music> getMusicByPlaylistId(int id) {
        info(this.getClass(), "Get music by playlist id");
        info(this.getClass(), "Playlist id: " + id);

        var musicList = new ArrayList<Music>();

        for (var playlistMusicModel : playlistMusicRepository.findAllById(id)) {
           var music = musicRepository.findAllById(playlistMusicModel.getMusicId());
            for (MusicModel musicModel : music) {
                musicList.add(playlistMusicFactory.createMusic(musicModel));
            }
        }

        info(this.getClass(), "Music list size: " + musicList.size());

        return musicList;
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
