package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.feign.Auth;
import com.music.musicStreamer.api.v1.repositories.UserRepository;
import com.music.musicStreamer.entities.user.User;
import com.music.musicStreamer.entities.user.UserAuth;
import com.music.musicStreamer.entities.user.UserAuthRequest;
import com.music.musicStreamer.entities.user.UserRequest;
import com.music.musicStreamer.gateways.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.music.musicStreamer.domain.models.UserModel;

import java.util.Date;
import java.util.Optional;


@Component
@AllArgsConstructor
public class UserClient implements UserGateway {
    private final UserRepository userRepository;
    private final Auth auth;

    @Override
    public User createUser(UserRequest userRequest) {
        final UserModel createdUser = this.userRepository.save(toModel(userRequest));
        return createdUser.toDomain();
    }

    @Override
    public UserAuth loginUser(UserAuthRequest userAuthRequest) {
        final Optional<UserModel> user = this.userRepository.findByEmailForAuthenticate(userAuthRequest.getEmail());
        if (user.isEmpty()) {
            throw new RuntimeException("User [" + userAuthRequest.getEmail() + "] not found");
        }
        return new UserAuth(auth.getToken(userAuthRequest));
    }


    public UserModel toModel(UserRequest userRequest) {
        UserModel userModel = new UserModel();
        userModel.setName(userRequest.getName());
        userModel.setEmail(userRequest.getEmail());
        userModel.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        userModel.setCreated_at(new Date());
        userModel.setUpdated_at(new Date());
        return userModel;
    }
}
