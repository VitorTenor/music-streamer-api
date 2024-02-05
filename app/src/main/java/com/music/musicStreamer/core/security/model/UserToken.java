package com.music.musicStreamer.core.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserToken {
    private final String userEmail;
    private final int userId;
}

