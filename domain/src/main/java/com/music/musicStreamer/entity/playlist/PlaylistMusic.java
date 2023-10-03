package com.music.musicStreamer.entity.playlist;

import com.music.musicStreamer.entity.music.Music;

import java.util.List;

public class PlaylistMusic {
    private int id;
    private String name;
    private int userId;

    private List<Music> music;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMusic(List<Music> music) {
        this.music = music;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public List<Music> getMusic() {
        return music;
    }

    public PlaylistMusic(int id, String name, int userId, List<Music> music) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.music = music;
    }
}
