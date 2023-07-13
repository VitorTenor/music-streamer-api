package com.music.musicStreamer.enumerators;

public enum PlaylistMessages {
    NOT_FOUND("Playlist not found"),
    MUSIC_ADDED("Music added to playlist"),

    NAME_REQUIRED("Name is required"),
    USER_ID_REQUIRED("User id is required"),

    MUSIC_ADDED_ERROR("Error adding music to playlist"),
    CREATE_PLAYLIST_ERROR("Error creating playlist"),
    GET_PLAYLIST_BY_USER_ID_ERROR("Error while getting playlist by user id"),
    GET_PLAYLIST_BY_PLAYLIST_ID_ERROR("Error while getting music by playlist id");

    private final String message;

    PlaylistMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
