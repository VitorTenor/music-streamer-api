package com.music.musicStreamer.usecase.user;


import com.music.musicStreamer.entity.user.UserLoginResponseEntity;
import com.music.musicStreamer.entity.user.UserAuthRequest;
import com.music.musicStreamer.gateway.UserGateway;

import javax.inject.Named;

@Named
public class LoginUserUseCase {
    private final UserGateway userGateway;

    public LoginUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserLoginResponseEntity execute(final UserAuthRequest userAuthRequest) {
        return this.userGateway.loginUser(userAuthRequest);
    }
}
