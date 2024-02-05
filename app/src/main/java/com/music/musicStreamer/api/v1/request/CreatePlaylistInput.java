package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entity.playlist.CreatePlaylistEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;

@Schema(name = "CreatePlaylistRequest", description = "Request to create a playlist")
public record CreatePlaylistInput(
        @NotEmpty(message = "Name is required")
        @Schema(description = "Name of the playlist", example = "My Playlist")
        String name
){
    public CreatePlaylistEntity toEntity(final int userId) {
        return new CreatePlaylistEntity(name, userId);
    }
}
