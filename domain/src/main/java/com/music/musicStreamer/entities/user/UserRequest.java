package com.music.musicStreamer.entities.user;

public class UserRequest {
    public UserRequest(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    private final String name;
    private final String email;
    private final String password;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
