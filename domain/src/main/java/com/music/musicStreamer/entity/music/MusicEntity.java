package com.music.musicStreamer.entity.music;

import com.music.musicStreamer.entity.image.ImageEntity;

public record MusicEntity (
        Integer id,
        String name,
        String artist,
        String album,
        String genre,
        ImageEntity imageEntity,
        String pathName
){
}
