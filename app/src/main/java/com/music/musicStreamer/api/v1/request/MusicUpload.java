package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entity.music.SaveMusicEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record MusicUpload(
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Artist is required")
        String artist,
        @NotEmpty(message = "Album is required")
        String album,
        @NotEmpty(message = "Genre is required")
        String genre,
        @NotNull(message = "Music is required")
        MultipartFile music
) {
    public SaveMusicEntity toEntity() throws Exception {
        return new SaveMusicEntity(name, artist, album, genre, music.getBytes());
    }
}
