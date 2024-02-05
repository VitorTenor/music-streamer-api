package com.music.musicStreamer.usecase.user;


import com.music.musicStreamer.entity.user.AuthenticationEntity;
import com.music.musicStreamer.entity.user.LoginEntity;
import com.music.musicStreamer.gateway.UserGateway;

import javax.inject.Named;

@Named
public class LoginUseCase {
    private final UserGateway userGateway;

    public LoginUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public AuthenticationEntity execute(LoginEntity entity) {
        return this.userGateway.login(entity);
    }
}
