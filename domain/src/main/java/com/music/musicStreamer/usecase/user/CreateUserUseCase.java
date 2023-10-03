package com.music.musicStreamer.usecase.user;

import com.music.musicStreamer.entity.user.User;
import com.music.musicStreamer.entity.user.UserRequest;
import com.music.musicStreamer.gateway.UserGateway;

import javax.inject.Named;

@Named
public class CreateUserUseCase {
    private final UserGateway userGateway;

    public CreateUserUseCase(final UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(final UserRequest userRequest) {
        return this.userGateway.createUser(userRequest);
    }

}
