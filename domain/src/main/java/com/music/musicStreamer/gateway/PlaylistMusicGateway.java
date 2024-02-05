package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.music.Music;

import java.util.List;

public interface PlaylistMusicGateway {
    Boolean deleteMusicFromPlaylist(int id);
    List<Music> getMusicByPlaylistId(int id);
    List<Long> getMusicIdByPlaylistId(final Long id);
}
