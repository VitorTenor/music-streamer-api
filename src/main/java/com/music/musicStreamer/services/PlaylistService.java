package com.music.musicStreamer.services;

import com.music.musicStreamer.models.PlaylistModel;
import com.music.musicStreamer.models.PlaylistMusicModel;
import com.music.musicStreamer.models.dtos.PlaylistDTO;
import com.music.musicStreamer.models.dtos.PlaylistMusicDTO;
import com.music.musicStreamer.repositories.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicService playlistMusicService;

    public PlaylistService(PlaylistRepository playlistRepository, PlaylistMusicService playlistMusicService) {
        this.playlistRepository = playlistRepository;
        this.playlistMusicService = playlistMusicService;
    }

    public PlaylistDTO createPlaylist(PlaylistModel playlist) {
        return toDto(savePlaylist(playlist));
    }
    private PlaylistModel savePlaylist(PlaylistModel playlist) {
        playlist.setCreated_at(new Date());
        playlist.setUpdated_at(new Date());
        return playlistRepository.save(playlist);
    }
    public PlaylistMusicModel addSongToPlaylist(PlaylistMusicModel playlistMusic) {
        return playlistMusicService.savePlaylistMusic(playlistMusic);
    }

    public PlaylistMusicDTO getPlaylistById(int playlistId) {
        return playlistMusicService.getPlaylistMusic(playlistId);
    }

    public ArrayList<PlaylistDTO> getPlaylistByUserId(int userId) {
        return toListDto(playlistRepository.findAllByUserId(userId));
    }

    private ArrayList<PlaylistDTO> toListDto(ArrayList<PlaylistModel> allByUserId) {
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        for (PlaylistModel playlist : allByUserId) {
            playlists.add(toDto(playlist));
        }
        return playlists;
    }

    private PlaylistDTO toDto(PlaylistModel newPlaylist) {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(newPlaylist.getId());
        playlistDTO.setUserId(newPlaylist.getUser_id());
        playlistDTO.setName(newPlaylist.getName());
        return playlistDTO;
    }
}
