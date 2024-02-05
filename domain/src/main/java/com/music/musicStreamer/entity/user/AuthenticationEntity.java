package com.music.musicStreamer.entity.user;

public record AuthenticationEntity(
        String name,
        String email,
        String token
) {
}
