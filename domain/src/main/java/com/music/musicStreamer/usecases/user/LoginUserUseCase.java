package com.music.musicStreamer.usecases.user;


import com.music.musicStreamer.entities.user.UserAuth;
import com.music.musicStreamer.entities.user.UserAuthRequest;
import com.music.musicStreamer.gateways.UserGateway;

import javax.inject.Named;

@Named
public class LoginUserUseCase {
    private final UserGateway userGateway;

    public LoginUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserAuth execute(final UserAuthRequest userAuthRequest) {
        return this.userGateway.loginUser(userAuthRequest);
    }
}
