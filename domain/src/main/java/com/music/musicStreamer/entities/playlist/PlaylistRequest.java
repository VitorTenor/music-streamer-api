package com.music.musicStreamer.entities.playlist;

import java.util.Objects;

public class PlaylistRequest {
    public PlaylistRequest(final String name, final int userId) {
        this.name = name;
        this.userId = userId;
    }

    private String name;
    private int userId;

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public Integer getUserId() {
        return userId;
    }

    public boolean validateUserId() {
        return Objects.isNull(userId);
    }
}
