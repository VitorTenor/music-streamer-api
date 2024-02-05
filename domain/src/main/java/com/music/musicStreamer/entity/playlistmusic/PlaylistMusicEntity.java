package com.music.musicStreamer.entity.playlistmusic;

public record PlaylistMusicEntity (
        Long playlistId,
        Long musicId,
        Long userId
){
}
