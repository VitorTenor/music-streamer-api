package com.music.musicStreamer.entities.user;

public class UserAuth {
    public UserAuth(final String token) {
        this.token = token;
    }


    private String token;

    public String getToken() {
        return token;
    }


}
