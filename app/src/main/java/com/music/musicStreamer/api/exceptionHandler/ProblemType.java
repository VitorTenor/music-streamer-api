package com.music.musicStreamer.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    SYSTEM_ERROR("/system-error", "System error"),
    MUSIC_ERROR("/music-error", "Music error"),
    USER_ERROR("/user-error", "User error"),
    UNEXPECTED_ERROR("/unexpected-error", "Unexpected error"),
    BUSINESS_ERROR("/business-error", "Business error"),
    IMAGE_ERROR("/image-error", "Image error"),
    PLAYLIST_ERROR("/playlist-error", "Playlist error"),
    PLAYLIST_MUSIC_ERROR("/playlist-music-error", "Playlist music error");
    private final String title;
    private final String uri;

    ProblemType(String uri, String title) {
        this.uri = uri;
        this.title = title;
    }
}
