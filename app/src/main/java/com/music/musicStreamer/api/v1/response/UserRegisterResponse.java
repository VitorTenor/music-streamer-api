package com.music.musicStreamer.api.v1.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegisterResponse {
    private String name;
    private String email;
}

