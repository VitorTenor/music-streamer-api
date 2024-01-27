package com.music.musicStreamer.usecase.user;

import com.music.musicStreamer.entity.user.UserRegisterResponseEntity;
import com.music.musicStreamer.entity.user.UserRegisterRequestEntity;
import com.music.musicStreamer.gateway.UserGateway;

import javax.inject.Named;

@Named
public class CreateUserUseCase {
    private final UserGateway userGateway;

    public CreateUserUseCase(final UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserRegisterResponseEntity execute(final UserRegisterRequestEntity userRegisterRequestEntity) {
        return this.userGateway.createUser(userRegisterRequestEntity);
    }

}
