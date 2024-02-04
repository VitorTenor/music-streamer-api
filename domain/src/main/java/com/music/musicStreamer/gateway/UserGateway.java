package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.user.CreateUserEntity;
import com.music.musicStreamer.entity.user.UserAuthRequest;
import com.music.musicStreamer.entity.user.UserLoginResponseEntity;

public interface UserGateway {
    CreateUserEntity create(CreateUserEntity entity);
    public UserLoginResponseEntity loginUser(UserAuthRequest userAuthRequest);
}
