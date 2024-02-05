package com.music.musicStreamer.entity.playlist;

import com.music.musicStreamer.entity.music.MusicEntity;

import java.util.List;

public record PlaylistWithMusicEntity(
        int id,
        String name,
        List<MusicEntity> musicEntities
) {
}
