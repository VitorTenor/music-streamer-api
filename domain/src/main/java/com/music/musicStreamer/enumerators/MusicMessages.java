package com.music.musicStreamer.enumerators;

public enum MusicMessages {
    NOT_FOUND("Music not found"),
    MUSIC_DELETED("Music deleted"),

    ALBUM_REQUIRED("Album is required"),
    ARTIST_REQUIRED("Artist is required"),
    NAME_REQUIRED("Name is required"),
    GENRE_REQUIRED("Genre is required"),
    MUSIC_REQUIRED("Music is required"),

    GET_ERROR("Error to get musics"),
    PLAY_ERROR("Error to play music"),
    SAVE_STORAGE_ERROR("Error saving music in storage"),
    DELETE_STORAGE_ERROR("Error deleting music in storage");

    private final String message;

    MusicMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
