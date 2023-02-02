package com.music.musicStreamer.core;

import com.music.musicStreamer.api.v1.models.MusicPlaylistRegisterDTO;
import com.music.musicStreamer.api.v1.models.MusicRegisterDTO;
import com.music.musicStreamer.api.v1.models.PlaylistRegisterDTO;
import com.music.musicStreamer.api.v1.models.UserRegisterDTO;
import com.music.musicStreamer.domain.models.MusicModel;
import com.music.musicStreamer.domain.models.PlaylistModel;
import com.music.musicStreamer.domain.models.PlaylistMusicModel;
import com.music.musicStreamer.domain.models.UserModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ToModel {

    public PlaylistModel toPlaylistModel(PlaylistRegisterDTO playlistRegisterDTO) {
        return PlaylistModel.builder()
                .name(playlistRegisterDTO.getName())
                .user_id(playlistRegisterDTO.getUserId())
                .build();
    }
    public PlaylistMusicModel toPlaylistMusicModel(MusicPlaylistRegisterDTO musicPlaylistRegisterDTO) {
        PlaylistMusicModel playlistMusicModel = new PlaylistMusicModel();
        playlistMusicModel.setPlaylist_id(musicPlaylistRegisterDTO.getPlaylistId());
        playlistMusicModel.setMusic_id(musicPlaylistRegisterDTO.getMusicId());
        playlistMusicModel.setUser_id(musicPlaylistRegisterDTO.getUserId());
        playlistMusicModel.setCreated_at(new Date());
        playlistMusicModel.setUpdated_at(new Date());
        return playlistMusicModel;
    }
    public MusicModel toMusicModel(MusicRegisterDTO musicRegisterDTO){
        MusicModel musicModel = new MusicModel();
        musicModel.setName(musicRegisterDTO.getName());
        musicModel.setArtist(musicRegisterDTO.getArtist());
        musicModel.setAlbum(musicRegisterDTO.getAlbum());
        musicModel.setGenre(musicRegisterDTO.getGenre());
        return musicModel;
    }

    public UserModel toUserModel(UserRegisterDTO userRegisterDTO) {
        UserModel userModel = new UserModel();
        userModel.setName(userRegisterDTO.getName());
        userModel.setEmail(userRegisterDTO.getEmail());
        userModel.setPassword(new BCryptPasswordEncoder().encode(userRegisterDTO.getPassword()));
        userModel.setCreated_at(new Date());
        userModel.setUpdated_at(new Date());
        return userModel;
    }
}
