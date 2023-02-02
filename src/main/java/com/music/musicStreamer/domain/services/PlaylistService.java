package com.music.musicStreamer.domain.services;

import com.music.musicStreamer.api.v1.models.MusicPlaylistRegisterDTO;
import com.music.musicStreamer.api.v1.models.PlaylistRegisterDTO;
import com.music.musicStreamer.core.ToDTO;
import com.music.musicStreamer.core.ToModel;
import com.music.musicStreamer.domain.models.PlaylistModel;
import com.music.musicStreamer.domain.models.PlaylistMusicModel;
import com.music.musicStreamer.domain.models.dtos.PlaylistDTO;
import com.music.musicStreamer.domain.models.dtos.PlaylistMusicDTO;
import com.music.musicStreamer.domain.repositories.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicService playlistMusicService;
    private final ToDTO toDTO;
    private final ToModel toModel;

    public PlaylistService(PlaylistRepository playlistRepository, PlaylistMusicService playlistMusicService, ToDTO toDTO, ToModel toModel) {
        this.playlistRepository = playlistRepository;
        this.playlistMusicService = playlistMusicService;
        this.toDTO = toDTO;
        this.toModel = toModel;
    }
    public PlaylistDTO createPlaylist(PlaylistRegisterDTO playlist) {
        return toDTO.toPlaylistDto(savePlaylist(toModel.toPlaylistModel(playlist)));
    }
    private PlaylistModel savePlaylist(PlaylistModel playlist) {
        return playlistRepository.save(playlist);
    }
    public PlaylistMusicModel addSongToPlaylist(MusicPlaylistRegisterDTO musicPlaylistRegisterDTO) {
        return playlistMusicService.savePlaylistMusic(musicPlaylistRegisterDTO);
    }
    public PlaylistMusicDTO getPlaylistById(int playlistId) {
        return playlistMusicService.getPlaylistMusic(playlistId);
    }
    public ArrayList<PlaylistDTO> getPlaylistByUserId(int userId) {
        return toDTO.toPlaylistListDto(playlistRepository.findAllByUserId(userId));
    }
}
