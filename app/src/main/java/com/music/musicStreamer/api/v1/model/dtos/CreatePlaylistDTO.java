package com.music.musicStreamer.api.v1.model.dtos;

import com.music.musicStreamer.entity.playlist.PlaylistRequest;
import lombok.Data;

@Data
public class CreatePlaylistDTO {
    private String name;
    private int userId;

    public PlaylistRequest toEntity() { return new PlaylistRequest(name, userId); }
}
