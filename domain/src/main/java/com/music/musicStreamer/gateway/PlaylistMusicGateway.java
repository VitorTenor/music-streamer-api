package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.playlistmusic.PlaylistMusicEntity;

import java.util.List;

public interface PlaylistMusicGateway {
    String create(PlaylistMusicEntity entity);
    Boolean deleteByMusicId(final Long id);
    List<MusicEntity> getMusicByPlaylistId(final Long id);
    List<Long> getMusicIdByPlaylistId(final Long id);
}
