package com.music.musicStreamer.gateways;

import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.entities.playlist.PlaylistMusic;
import com.music.musicStreamer.entities.playlist.PlaylistRequest;

import java.util.List;

public interface PlaylistGateway {
    public Playlist createPlaylist(PlaylistRequest playlistRequest) throws Exception;

    public String addMusicToPlaylist(MusicPlaylistRequest musicPlaylistRequest);

    public PlaylistMusic getPlaylistById(int id);

    public List<Playlist> getPlaylistByUserId(int id);
}
