package com.music.musicStreamer.usecases.user;

import com.music.musicStreamer.entities.user.User;
import com.music.musicStreamer.entities.user.UserRequest;
import com.music.musicStreamer.gateways.UserGateway;

import javax.inject.Named;

@Named
public class CreateUserUseCase {
    private final UserGateway userGateway;

    public CreateUserUseCase(final UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(final UserRequest userRequest) {
        try {
            return this.userGateway.createUser(userRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
