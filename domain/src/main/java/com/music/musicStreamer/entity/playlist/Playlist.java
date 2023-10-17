package com.music.musicStreamer.entity.playlist;

public class Playlist {

    private int id;
    private String name;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Playlist(String name, int id) {
        this.name = name;
        this.id = id;
    }

}
