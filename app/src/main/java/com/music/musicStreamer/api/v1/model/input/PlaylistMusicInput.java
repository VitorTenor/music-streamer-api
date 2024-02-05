package com.music.musicStreamer.api.v1.model.input;

import com.music.musicStreamer.entity.playlistmusic.PlaylistMusicEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PlaylistMusicInput", description = "Playlist music input")
public record PlaylistMusicInput (
        @Schema(description = "Music id", example = "1")
        Long musicId,
        @Schema(description = "Playlist id", example = "1")
        Long playlistId
){
    public PlaylistMusicEntity toEntity(final Long userId) {
        return new PlaylistMusicEntity(this.playlistId, this.musicId, userId);
    }
}
