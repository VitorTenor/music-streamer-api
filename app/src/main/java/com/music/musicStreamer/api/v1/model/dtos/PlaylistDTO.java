package com.music.musicStreamer.api.v1.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistDTO {
    private int id;
    private int userId;
    private String name;
}
