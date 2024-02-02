package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import com.music.musicStreamer.entity.playlist.PlaylistMusic;
import com.music.musicStreamer.entity.playlist.CreatePlaylistEntity;

import java.util.List;

public interface PlaylistGateway {
    PlaylistEntity create(CreatePlaylistEntity createPlaylistEntity);

    String addMusicToPlaylist(MusicPlaylistRequest musicPlaylistRequest);

    PlaylistMusic getPlaylistById(int id);

    List<PlaylistEntity> getPlaylistByUserId(int id);
}
