package com.music.musicStreamer.entity.playlist;

import com.music.musicStreamer.entity.music.Music;

import java.util.List;

public record PlaylistMusicEntity (
        int id,
        String name,
        int userId,
        List<Music> music
){
}
