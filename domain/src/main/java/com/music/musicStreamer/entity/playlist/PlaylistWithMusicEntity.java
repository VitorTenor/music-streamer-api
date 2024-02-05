package com.music.musicStreamer.entity.playlist;

import com.music.musicStreamer.entity.music.Music;

import java.util.List;

public record PlaylistWithMusicEntity(
        int id,
        String name,
        List<Music> music
) {
}
