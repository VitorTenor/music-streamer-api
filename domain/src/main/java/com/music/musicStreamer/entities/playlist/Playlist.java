package com.music.musicStreamer.entities.playlist;

public class Playlist {

    private int id;
    private String name;
    private int userId;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Playlist(String name, int userId, int id) {
        this.name = name;
        this.userId = userId;
        this.id = id;
    }

}
