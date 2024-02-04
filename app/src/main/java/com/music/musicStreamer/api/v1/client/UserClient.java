package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.database.model.UserModel;
import com.music.musicStreamer.api.v1.database.repository.UserRepository;
import com.music.musicStreamer.core.security.service.TokenService;
import com.music.musicStreamer.core.util.factory.UserRegisterFactory;
import com.music.musicStreamer.entity.user.UserRegisterResponseEntity;
import com.music.musicStreamer.entity.user.UserLoginResponseEntity;
import com.music.musicStreamer.entity.user.UserAuthRequest;
import com.music.musicStreamer.entity.user.UserRegisterRequestEntity;
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
    private final UserRegisterFactory userRegisterFactory;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserRegisterResponseEntity createUser(UserRegisterRequestEntity user) {
        log.info("[UserClient] Create user");

        final var existsByEmail = userRepository.existsByEmail(user.email());
        if (Boolean.TRUE.equals(existsByEmail)) throw new UserException(UserMessages.ALREADY_EXISTS);

        final var createdUser = save(userRegisterFactory.toModel(user));

        log.info("[UserClient] User created => userId: " + createdUser.getId());

        return userRegisterFactory.toEntity(createdUser);
    }

    @Override
    public UserLoginResponseEntity loginUser(final UserAuthRequest userAuthRequest) {
        log.info("[UserClient] Login user");
        final var user = userRepository.findByEmail(userAuthRequest.getEmail())
                .orElseThrow(() -> {
                    log.info("[UserClient] User not found => " + userAuthRequest.getEmail());
                    return new UserException(UserMessages.NOT_FOUND);
                });

        authClient.authenticate(userAuthRequest);
        final var token = tokenService.generateToken(user);

        log.info("[UserClient] User logged => " + user.getEmail());
        return new UserLoginResponseEntity(user.getName(), user.getEmail(), token);
    }

    private UserModel save(UserModel userModel) {
        log.info("[UserClient] Save user => " + userModel.getEmail());
        return this.userRepository.save(userModel);
    }

}
