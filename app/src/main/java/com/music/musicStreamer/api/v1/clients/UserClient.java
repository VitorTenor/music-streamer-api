package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.UserModel;
import com.music.musicStreamer.api.v1.repositories.UserRepository;
import com.music.musicStreamer.core.token.AuthService;
import com.music.musicStreamer.core.utils.factories.UserFactory;
import com.music.musicStreamer.core.utils.validators.UserValidator;
import com.music.musicStreamer.entities.user.User;
import com.music.musicStreamer.entities.user.UserAuth;
import com.music.musicStreamer.entities.user.UserAuthRequest;
import com.music.musicStreamer.entities.user.UserRequest;
import com.music.musicStreamer.gateways.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.logging.Logger;


@Component
@AllArgsConstructor
public class UserClient implements UserGateway {
    private final UserFactory userFactory;
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final Logger logger = Logger.getLogger(UserClient.class.getName());

    @Override
    @Transactional
    public User createUser(UserRequest userRequest) {
        logger.info("[UserClient] Create user");
        userValidator.validateUser(userRequest);

        UserModel createdUser = save(userFactory.createUserModel(userRequest));

        logger.info("[UserClient] User created => " + createdUser.getEmail());

        return userFactory.createUser(createdUser);
    }

    @Override
    public UserAuth loginUser(UserAuthRequest userAuthRequest) {
        logger.info("[UserClient] Login user");
        UserModel user = userValidator.validateUserLogin(userAuthRequest);

        String userDetails = authService.authenticateUser(userAuthRequest.getEmail(), userAuthRequest.getPassword());

        logger.info("[UserClient] User logged => " + user.getEmail());
        return new UserAuth(user.getId(), user.getName(), user.getEmail(), userDetails);
    }

    private UserModel save(UserModel userModel) {
        return this.userRepository.save(userModel);
    }

}
