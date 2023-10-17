package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.model.MusicModel;
import com.music.musicStreamer.api.v1.model.PlaylistMusicModel;
import com.music.musicStreamer.api.v1.repository.MusicRepository;
import com.music.musicStreamer.api.v1.repository.PlaylistMusicRepository;
import com.music.musicStreamer.core.util.factory.PlaylistMusicFactory;
import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.gateway.PlaylistMusicGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class PlaylistMusicClient implements PlaylistMusicGateway {

    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final PlaylistMusicFactory playlistMusicFactory;
    private final Logger LOGGER = Logger.getLogger(PlaylistMusicClient.class.getName());

    @Override
    @Transactional
    public Boolean deleteMusicFromPlaylist(int id) {
        LOGGER.info("[PlaylistMusicClient] Delete music from playlist");

        List<PlaylistMusicModel> playlistMusic = playlistMusicRepository.findByMusicId(id);
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
}
