package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.user.AuthenticationEntity;
import com.music.musicStreamer.entity.user.CreateUserEntity;
import com.music.musicStreamer.entity.user.LoginEntity;
import com.music.musicStreamer.entity.user.UserEntity;

public interface UserGateway {
    UserEntity create(CreateUserEntity entity);
    AuthenticationEntity login(LoginEntity entity);
}
