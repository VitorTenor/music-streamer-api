package com.music.musicStreamer.entity.playlist;

import java.util.List;

public record PlaylistEntity (
        Long id,
        String name,
        List<Long> musicIds
){
}
