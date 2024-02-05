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
import com.music.musicStreamer.gateway.AuthenticationGateway;
import com.music.musicStreamer.gateway.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
@AllArgsConstructor
public class UserClient implements UserGateway {
    /*
     * Clients
     */
    private final AuthenticationGateway authenticationGateway;

    /*
     * Factories
     */
    private final UserFactory userFactory;
    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserEntity create(CreateUserEntity entity) {
        info(this.getClass(), "Create user");

        final var existsByEmail = userRepository.existsByEmail(entity.email());
        if (Boolean.TRUE.equals(existsByEmail)) throw new UserException(UserMessages.ALREADY_EXISTS);

        final var userModel = userFactory.toModel(entity);
        final var createdUser = this.save(userModel);

        info(this.getClass(), "User created => userId: " + createdUser.getId());

        return userFactory.toEntity(createdUser);
    }

    @Override
    public AuthenticationEntity login(LoginEntity entity) {
        info(this.getClass(), "Login user");

        final var user = userRepository.findByEmail(entity.email())
                .orElseThrow(() -> {
                    info(this.getClass(), "User not found => " + entity.email());
                    return new UserException(UserMessages.NOT_FOUND);
                });

        if (Boolean.TRUE.equals(authenticationGateway.isAuthenticated(entity))) {
            final var token = tokenService.generate(user);
            info(this.getClass(), "User logged => " + user.getEmail());
            return userFactory.toEntity(user, token);
        }
        info(this.getClass(), "User not authenticated => " + entity.email());
        throw new UserException(UserMessages.UNAUTHORIZED);
    }

    private UserModel save(final UserModel userModel) {
        info(this.getClass(), "Save user => " + userModel.getEmail());
        return userRepository.save(userModel);
    }
}
