package com.music.musicStreamer.gateways;

import com.music.musicStreamer.entities.music.Music;

import java.util.List;

public interface PlaylistMusicGateway {
    public Boolean deleteMusicFromPlaylist(int id);
    public List<Music> getMusicByPlaylistId(int id);
}
