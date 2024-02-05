package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.user.LoginEntity;

public interface AuthenticationGateway {
    Boolean isAuthenticated(LoginEntity entity);
}
