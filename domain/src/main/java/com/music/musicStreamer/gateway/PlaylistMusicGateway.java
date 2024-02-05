package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.entity.playlistmusic.PlaylistMusicEntity;

import java.util.List;

public interface PlaylistMusicGateway {
    String create(PlaylistMusicEntity entity);
    Boolean delete(int id);
    List<Music> getMusicByPlaylistId(int id);
    List<Long> getMusicIdByPlaylistId(final Long id);
}
