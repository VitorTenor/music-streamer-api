package com.music.musicStreamer.entities.user;

public class UserAuth {
    public UserAuth(final String token) {
        this.token = token;
    }

    private int id;
    private String email;
    private String token;

    public UserAuth(int id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
