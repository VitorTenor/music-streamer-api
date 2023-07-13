package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.MusicModel;
import com.music.musicStreamer.api.v1.models.PlaylistMusicModel;
import com.music.musicStreamer.api.v1.repositories.MusicRepository;
import com.music.musicStreamer.api.v1.repositories.PlaylistMusicRepository;
import com.music.musicStreamer.core.utils.factories.PlaylistMusicFactory;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.gateways.PlaylistMusicGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaylistMusicClient implements PlaylistMusicGateway {

    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final PlaylistMusicFactory playlistMusicFactory;

    @Override
    @Transactional
    public Boolean deleteMusicFromPlaylist(int id) {
            List<PlaylistMusicModel> playlistMusic = playlistMusicRepository.findByMusicId(id);
            for (PlaylistMusicModel playlistMusicModel : playlistMusic) {
                playlistMusicRepository.deleteById(playlistMusicModel.getId());
            }
            return true;
    }

    @Override
    public List<Music> getMusicByPlaylistId(int id) {
            List<Music> musicList = new ArrayList<>();

            for (PlaylistMusicModel playlistMusicModel : playlistMusicRepository.findAllById(id)) {
                List<MusicModel> music = musicRepository.findAllById(playlistMusicModel.getMusicId());

                for (MusicModel musicModel : music) {
                    musicList.add(playlistMusicFactory.createMusic(musicModel));
                }
            }

            return musicList;
    }
}
