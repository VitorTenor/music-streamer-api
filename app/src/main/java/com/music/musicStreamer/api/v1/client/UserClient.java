package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.database.model.UserModel;
import com.music.musicStreamer.api.v1.database.repository.UserRepository;
import com.music.musicStreamer.core.security.service.TokenService;
import com.music.musicStreamer.core.util.factory.UserFactory;
import com.music.musicStreamer.entity.user.AuthenticationEntity;
import com.music.musicStreamer.entity.user.CreateUserEntity;
import com.music.musicStreamer.entity.user.LoginEntity;
import com.music.musicStreamer.entity.user.UserEntity;
import com.music.musicStreamer.enums.UserMessages;
import com.music.musicStreamer.exception.UserException;
import com.music.musicStreamer.gateway.UserGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class UserClient implements UserGateway {
    private final AuthClient authClient;
    private final UserFactory userFactory;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserEntity create(CreateUserEntity entity) {
        log.info("[UserClient] Create user");

        final var existsByEmail = userRepository.existsByEmail(entity.email());
        if (Boolean.TRUE.equals(existsByEmail)) throw new UserException(UserMessages.ALREADY_EXISTS);

        final var userModel = userFactory.toModel(entity);
        final var createdUser = save(userModel);

        log.info("[UserClient] User created => userId: " + createdUser.getId());

        return userFactory.toEntity(createdUser);
    }

    @Override
    public AuthenticationEntity login(LoginEntity entity) {
        log.info("[UserClient] Login user");

        final var user = userRepository.findByEmail(entity.email())
                .orElseThrow(() -> {
                    log.info("[UserClient] User not found => " + entity.email());
                    return new UserException(UserMessages.NOT_FOUND);
                });

        authClient.authenticate(entity);
        final var token = tokenService.generateToken(user);

        log.info("[UserClient] User logged => " + user.getEmail());
        return userFactory.toEntity(user, token);
    }

    private UserModel save(final UserModel userModel) {
        log.info("[UserClient] Save user => " + userModel.getEmail());
        return this.userRepository.save(userModel);
    }

}
