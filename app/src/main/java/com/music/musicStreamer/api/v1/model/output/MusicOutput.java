package com.music.musicStreamer.api.v1.model.output;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicOutput {
    private final Integer id;
    private final String name;
    private final String artist;
    private final String album;
    private final String genre;
    private final String pathName;
}
