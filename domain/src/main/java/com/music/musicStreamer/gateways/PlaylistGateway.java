package com.music.musicStreamer.gateways;

import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.entities.playlist.PlaylistMusic;
import com.music.musicStreamer.entities.playlist.PlaylistRequest;

import java.util.List;

public interface PlaylistGateway {
    Playlist createPlaylist(PlaylistRequest playlistRequest);

    String addMusicToPlaylist(MusicPlaylistRequest musicPlaylistRequest);

    PlaylistMusic getPlaylistById(int id);

    List<Playlist> getPlaylistByUserId(int id);
}
