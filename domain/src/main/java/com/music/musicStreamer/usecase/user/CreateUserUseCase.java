package com.music.musicStreamer.usecase.user;

import com.music.musicStreamer.entity.user.CreateUserEntity;
import com.music.musicStreamer.gateway.UserGateway;

import javax.inject.Named;

@Named
public class CreateUserUseCase {
    private final UserGateway userGateway;

    public CreateUserUseCase(final UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public CreateUserEntity execute(final CreateUserEntity entity) {
        return this.userGateway.create(entity);
    }

}
