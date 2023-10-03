package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.music.Music;

import java.util.List;

public interface PlaylistMusicGateway {
    public Boolean deleteMusicFromPlaylist(int id);
    public List<Music> getMusicByPlaylistId(int id);
}
