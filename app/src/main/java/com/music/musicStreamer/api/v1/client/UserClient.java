package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.model.UserModel;
import com.music.musicStreamer.api.v1.repository.UserRepository;
import com.music.musicStreamer.core.security.service.TokenService;
import com.music.musicStreamer.core.util.factory.UserFactory;
import com.music.musicStreamer.core.util.validator.UserValidator;
import com.music.musicStreamer.entity.user.UserRegisterEntity;
import com.music.musicStreamer.entity.user.UserAuth;
import com.music.musicStreamer.entity.user.UserAuthRequest;
import com.music.musicStreamer.entity.user.UserRequest;
import com.music.musicStreamer.gateway.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.logging.Logger;


@Component
@AllArgsConstructor
public class UserClient implements UserGateway {
    private final AuthClient authClient;
    private final UserFactory userFactory;
    private final TokenService tokenService;
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final Logger LOGGER = Logger.getLogger(UserClient.class.getName());

    @Override
    @Transactional
    public UserRegisterEntity createUser(UserRequest userRequest) {
        LOGGER.info("[UserClient] Create user");
        userValidator.validateUser(userRequest);

        UserModel createdUser = save(userFactory.createUserModel(userRequest));

        LOGGER.info("[UserClient] User created => userId: " + createdUser.getId());

        return userFactory.createUser(createdUser);
    }

    @Override
    public UserAuth loginUser(UserAuthRequest userAuthRequest) {
        LOGGER.info("[UserClient] Login user");
        UserModel user = userValidator.validateUserLogin(userAuthRequest);

        authClient.authenticate(userAuthRequest);

        LOGGER.info("[UserClient] User logged => " + user.getEmail());
        return new UserAuth(user.getName(), user.getEmail(), tokenService.generateToken(user));
    }

    private UserModel save(UserModel userModel) {
        LOGGER.info("[UserClient] Save user => " + userModel.getEmail());
        return this.userRepository.save(userModel);
    }

}
