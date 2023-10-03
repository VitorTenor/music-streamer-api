package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entity.playlist.Playlist;
import com.music.musicStreamer.entity.playlist.PlaylistMusic;
import com.music.musicStreamer.entity.playlist.PlaylistRequest;

import java.util.List;

public interface PlaylistGateway {
    Playlist createPlaylist(PlaylistRequest playlistRequest);

    String addMusicToPlaylist(MusicPlaylistRequest musicPlaylistRequest);

    PlaylistMusic getPlaylistById(int id);

    List<Playlist> getPlaylistByUserId(int id);
}
