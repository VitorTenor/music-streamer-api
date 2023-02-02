package com.music.musicStreamer.core;

import com.music.musicStreamer.domain.models.MusicModel;
import com.music.musicStreamer.domain.models.PlaylistModel;
import com.music.musicStreamer.domain.models.UserModel;
import com.music.musicStreamer.domain.models.dtos.MusicDTO;
import com.music.musicStreamer.domain.models.dtos.PlaylistDTO;
import com.music.musicStreamer.domain.models.dtos.UserDTO;
import com.music.musicStreamer.domain.services.ImageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToDTO {

    private final ImageService imageService;

    public ToDTO(ImageService imageService) {
        this.imageService = imageService;
    }

    public ArrayList<PlaylistDTO> toPlaylistListDto(ArrayList<PlaylistModel> allByUserId) {
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        for (PlaylistModel playlist : allByUserId) {
            playlists.add(toPlaylistDto(playlist));
        }
        return playlists;
    }

    public PlaylistDTO toPlaylistDto(PlaylistModel newPlaylist) {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(newPlaylist.getId());
        playlistDTO.setUserId(newPlaylist.getUserId());
        playlistDTO.setName(newPlaylist.getName());
        return playlistDTO;
    }

    public MusicDTO toMusiclDTO(MusicModel musicModel) {
        MusicDTO musicDTO = new MusicDTO();
        musicDTO.setId(musicModel.getId());
        musicDTO.setName(musicModel.getName());
        musicDTO.setArtist(musicModel.getArtist());
        musicDTO.setAlbum(musicModel.getAlbum());
        musicDTO.setGenre(musicModel.getGenre());
        musicDTO.setPath_name(musicModel.getPathName());
        musicDTO.setImage(imageService.getImageByMusicId(musicModel.getId()));
        return musicDTO;
    }

    public List<MusicDTO> listToMusicDTO(List<MusicModel> musicModel) {
        List<MusicDTO> musicDTO = new ArrayList<>();
        for (MusicModel music : musicModel) {
            MusicDTO musicDTO1 = new MusicDTO();
            musicDTO1.setId(music.getId());
            musicDTO1.setName(music.getName());
            musicDTO1.setArtist(music.getArtist());
            musicDTO1.setAlbum(music.getAlbum());
            musicDTO1.setGenre(music.getGenre());
            musicDTO1.setPath_name(music.getPathName());
            musicDTO1.setImage(imageService.getImageByMusicId(music.getId()));
            musicDTO.add(musicDTO1);
        }
        return musicDTO;
    }

    public UserDTO toUserDTO(UserModel userModel) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userModel.getId());
        userDTO.setName(userModel.getName());
        userDTO.setEmail(userModel.getEmail());
        return userDTO;
    }
}
