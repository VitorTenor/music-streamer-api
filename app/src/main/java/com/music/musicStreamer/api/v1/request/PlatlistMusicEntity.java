package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;

import javax.validation.constraints.NotEmpty;

public record PlatlistMusicEntity(
        @NotEmpty(message = "Music id is required")
        int musicId,
        @NotEmpty(message = "Playlist id is required")
        int playlistId
){

    public MusicPlaylistRequest toEntity(int userId) {
        return new MusicPlaylistRequest(playlistId, musicId, userId);
    }
}
