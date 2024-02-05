package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.playlist.CreatePlaylistEntity;
import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import com.music.musicStreamer.entity.playlist.PlaylistMusicEntity;

import java.util.List;

public interface PlaylistGateway {
    PlaylistEntity create(CreatePlaylistEntity createPlaylistEntity);

    String addMusicToPlaylist(MusicPlaylistRequest musicPlaylistRequest);

    PlaylistMusicEntity getPlaylistById(int id);

    List<PlaylistEntity> getPlaylistByUserId(int id);
}
