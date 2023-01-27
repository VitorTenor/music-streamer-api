package com.music.musicStreamer.services;

import com.music.musicStreamer.models.MusicModel;
import com.music.musicStreamer.models.PlaylistMusicModel;
import com.music.musicStreamer.models.dtos.MusicDTO;
import com.music.musicStreamer.models.dtos.PlaylistMusicDTO;
import com.music.musicStreamer.repositories.MusicRepository;
import com.music.musicStreamer.repositories.PlaylistMusicRepository;
import com.music.musicStreamer.repositories.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistMusicService {
    private final PlaylistMusicRepository playlistMusicRepository;
    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;

    public PlaylistMusicService(PlaylistMusicRepository playlistMusicRepository, PlaylistRepository playlistRepository, MusicRepository musicRepository) {
        this.playlistMusicRepository = playlistMusicRepository;
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
    }

    public PlaylistMusicModel savePlaylistMusic(PlaylistMusicModel playlistMusic) {
        Boolean playlistExists = playlistRepository.existsById(playlistMusic.getPlaylist_id());
        Boolean musicExists = musicRepository.existsById(playlistMusic.getMusic_id());
        if (playlistExists && musicExists) {
            playlistMusic.setCreated_at(new Date());
            playlistMusic.setUpdated_at(new Date());
            return playlistMusicRepository.save(playlistMusic);
        }
        return null;
    }
    public PlaylistMusicDTO getPlaylistMusic(int playlistId) {
        PlaylistMusicDTO playlistMusic = new PlaylistMusicDTO();
        playlistMusic.setPlaylist_id(playlistRepository.findById(playlistId).get().getId());
        playlistMusic.setUser_id(playlistRepository.findById(playlistId).get().getUser_id());
        playlistMusic.setMusics(getMusics(playlistId));
        return playlistMusic;
    }
    private ArrayList<MusicDTO> getMusics(int playlistId) {
        ArrayList<MusicDTO> musics = new ArrayList<>();
        ArrayList<PlaylistMusicModel> musicModels = playlistMusicRepository.findByPlaylistId2(playlistId);
        System.out.println(musicModels);
        for (PlaylistMusicModel musicModel : musicModels) {
            Optional<MusicModel> music = musicRepository.findById(musicModel.getMusic_id());
            if (music.isPresent()) {
                MusicDTO musicDTO = new MusicDTO();
                musicDTO.setId(music.get().getId());
                musicDTO.setName(music.get().getName());
                musicDTO.setArtist(music.get().getArtist());
                musicDTO.setAlbum(music.get().getAlbum());
                musicDTO.setGenre(music.get().getGenre());
                musics.add(musicDTO);
            }
        }
        return musics;
    }

    public void deleteAllByMusicId(Integer id) {
        List<PlaylistMusicModel> playlistMusic = playlistMusicRepository.findByMusicId(id);
        for (PlaylistMusicModel playlistMusicModel : playlistMusic) {
            playlistMusicRepository.deleteById(playlistMusicModel.getId());
        }
    }
}
