package com.music.musicStreamer.api.v1.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponse {
    private final String name;
    private final String email;
    private final String token;
}
