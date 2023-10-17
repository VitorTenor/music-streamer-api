package com.music.musicStreamer.entity.user;

public class User {

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    private final String name;
    private final String email;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
