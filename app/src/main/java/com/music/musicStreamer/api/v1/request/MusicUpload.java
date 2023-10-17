package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entity.music.MusicRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

public record MusicUpload(
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Artist is required")
        String artist,
        @NotEmpty(message = "Album is required")
        String album,
        @NotEmpty(message = "Genre is required")
        String genre,
        @NotEmpty(message = "Music is required")
        MultipartFile music
) {
    public MusicRequest toEntity() throws Exception {
        return new MusicRequest(name, artist, album, genre, music.getBytes());
    }
}