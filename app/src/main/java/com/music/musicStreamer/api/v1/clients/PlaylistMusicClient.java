package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.MusicModel;
import com.music.musicStreamer.api.v1.models.PlaylistMusicModel;
import com.music.musicStreamer.api.v1.repositories.MusicRepository;
import com.music.musicStreamer.api.v1.repositories.PlaylistMusicRepository;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.exceptions.PlaylistMusicException;
import com.music.musicStreamer.gateways.PlaylistMusicGateway;
import com.music.musicStreamer.usecases.image.GetImageByMusicIdUseCase;
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
    private final GetImageByMusicIdUseCase getImageByMusicIdUseCase;
    @Override
    @Transactional
    public Boolean deleteMusicFromPlaylist(int id) {
        try {
            List<PlaylistMusicModel> playlistMusic = playlistMusicRepository.findByMusicId(id);
            for (PlaylistMusicModel playlistMusicModel : playlistMusic) {
                playlistMusicRepository.deleteById(playlistMusicModel.getId());
            }
            return true;
        } catch (Exception e) {
            throw new PlaylistMusicException("Error deleting music from playlist");
        }
    }

    @Override
    public List<Music> getMusicByPlaylistId(int id) {
        try {
            List<Music> musicList = new ArrayList<>();
            List<PlaylistMusicModel> playlistMusic = playlistMusicRepository.findAllById(id);
            for (PlaylistMusicModel playlistMusicModel : playlistMusic) {
                List<MusicModel> music = musicRepository.findAllById(playlistMusicModel.getMusicId());
                for (MusicModel musicModel : music) {
                    Music music1 = new Music();
                    music1.setId(musicModel.getId());
                    music1.setName(musicModel.getName());
                    music1.setArtist(musicModel.getArtist());
                    music1.setAlbum(musicModel.getAlbum());
                    music1.setGenre(musicModel.getGenre());
                    music1.setPath_name(musicModel.getPathName());
                    music1.setImage(getImageByMusicIdUseCase.execute(musicModel.getId()));
                    musicList.add(music1);
                }
            }
            return musicList;
        } catch (Exception e) {
            throw new PlaylistMusicException("Error getting music by playlist id");
        }
    }
}
