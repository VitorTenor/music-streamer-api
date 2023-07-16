package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.UserModel;
import com.music.musicStreamer.api.v1.repositories.UserRepository;
import com.music.musicStreamer.core.feign.Auth;
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
    private final Auth auth;
    private final UserFactory userFactory;
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(UserClient.class.getName());

    @Override
    @Transactional
    public User createUser(UserRequest userRequest) {
        userValidator.validateUser(userRequest);

        UserModel createdUser = save(userFactory.createUserModel(userRequest));

        logger.info("User created :" + createdUser.getEmail());

        return userFactory.createUser(createdUser);
    }

    @Override
    public UserAuth loginUser(UserAuthRequest userAuthRequest) {
        UserModel user = userValidator.validateUserLogin(userAuthRequest);
        logger.info("User logged :" + user.getEmail());
        return new UserAuth(user.getId(), user.getName(),user.getEmail(), auth.getToken(userAuthRequest));
    }

    private UserModel save(UserModel userModel) {
        return this.userRepository.save(userModel);
    }

}
