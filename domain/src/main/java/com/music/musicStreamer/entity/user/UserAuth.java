package com.music.musicStreamer.entity.user;

public class UserAuth {
    public UserAuth(final String token) {
        this.token = token;
    }

    private String email;
    private String name;
    private final String token;

    public UserAuth(String name, String email, String token) {
        this.name = name;
        this.email = email;
        this.token = token;
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
