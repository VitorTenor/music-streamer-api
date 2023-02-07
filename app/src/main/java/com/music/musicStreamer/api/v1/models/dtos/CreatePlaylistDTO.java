package com.music.musicStreamer.api.v1.models.dtos;

import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entities.playlist.PlaylistRequest;
import lombok.Data;

@Data
public class CreatePlaylistDTO {
    private String name;
    private int userId;

    public PlaylistRequest toRequest() { return new PlaylistRequest(name, userId); }
}
