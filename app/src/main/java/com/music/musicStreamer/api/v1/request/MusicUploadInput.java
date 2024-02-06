package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entity.music.SaveMusicEntity;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.exception.MusicException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record MusicUploadInput(
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
    public SaveMusicEntity toEntity() {
        try {
            return new SaveMusicEntity(name, artist, album, genre, music.getBytes());
        } catch (Exception e) {
            throw new MusicException(MusicMessages.SAVE_STORAGE_ERROR);
        }
    }
}
