package com.music.musicStreamer.api.v1.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MusicPlaylistRegisterDTO {
    private int playlistId;
    private int musicId;
    private int userId;
}
