package com.music.musicStreamer.api.v1.model.output;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaylistWithMusicOutput {
    private final Long id;
    private final String name;
    private final List<MusicOutput> music;
}
