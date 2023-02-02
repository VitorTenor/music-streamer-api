package com.music.musicStreamer.domain.services;

import com.music.musicStreamer.api.v1.models.MusicPlaylistRegisterDTO;
import com.music.musicStreamer.core.ToModel;
import com.music.musicStreamer.domain.models.MusicModel;
import com.music.musicStreamer.domain.models.PlaylistMusicModel;
import com.music.musicStreamer.domain.models.dtos.MusicDTO;
import com.music.musicStreamer.domain.models.dtos.PlaylistMusicDTO;
import com.music.musicStreamer.domain.repositories.MusicRepository;
import com.music.musicStreamer.domain.repositories.PlaylistMusicRepository;
import com.music.musicStreamer.domain.repositories.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistMusicService {
    private final PlaylistMusicRepository playlistMusicRepository;
    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;
    private final ToModel toModel;

    public PlaylistMusicService(PlaylistMusicRepository playlistMusicRepository, PlaylistRepository playlistRepository, MusicRepository musicRepository, ToModel toModel) {
        this.playlistMusicRepository = playlistMusicRepository;
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
        this.toModel = toModel;
    }

    public PlaylistMusicModel savePlaylistMusic(MusicPlaylistRegisterDTO musicPlaylistRegisterDTO) {
        Boolean playlistExists = playlistRepository.existsById(musicPlaylistRegisterDTO.getPlaylistId());
        Boolean musicExists = musicRepository.existsById(musicPlaylistRegisterDTO.getMusicId());
        if (playlistExists && musicExists) {
            PlaylistMusicModel playlistMusic = toModel.toPlaylistMusicModel(musicPlaylistRegisterDTO);
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
