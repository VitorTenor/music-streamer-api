package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entity.playlist.PlaylistRequest;

import javax.validation.constraints.NotEmpty;

public record PlaylistRegister(
        @NotEmpty(message = "Name is required")
        String name
) {

    public PlaylistRequest toEntity(int userId) {
        return new PlaylistRequest(name, userId);
    }
}
