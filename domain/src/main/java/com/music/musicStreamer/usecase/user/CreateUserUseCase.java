package com.music.musicStreamer.usecase.user;

import com.music.musicStreamer.entity.user.UserRegisterEntity;
import com.music.musicStreamer.entity.user.UserRequest;
import com.music.musicStreamer.gateway.UserGateway;

import javax.inject.Named;

@Named
public class CreateUserUseCase {
    private final UserGateway userGateway;

    public CreateUserUseCase(final UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserRegisterEntity execute(final UserRequest userRequest) {
        return this.userGateway.createUser(userRequest);
    }

}
