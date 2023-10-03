package com.music.musicStreamer.entity.user;

public class User {

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    private final int id;
    private final String name;
    private final String email;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
