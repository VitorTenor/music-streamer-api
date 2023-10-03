package com.music.musicStreamer.entity.user;

public class UserAuth {
    public UserAuth(final String token) {
        this.token = token;
    }

    private int id;
    private String email;
    private String name;
    private final String token;

    public UserAuth(int id, String name, String email, String token) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
