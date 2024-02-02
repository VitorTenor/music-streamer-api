package com.music.musicStreamer.api.v1.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePlaylistResponse {
    private final Long id;
    private final String name;
}
