package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.model.UserModel;
import com.music.musicStreamer.entities.user.User;
import com.music.musicStreamer.entities.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserFactory {

    public UserModel createUserModel(UserRequest userRequest) {
        UserModel userModel = new UserModel();
        userModel.setName(userRequest.getName());
        userModel.setEmail(userRequest.getEmail());
        userModel.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        userModel.setCreated_at(new Date());
        userModel.setUpdated_at(new Date());
        return userModel;
    }

    public User createUser(UserModel userModel) {
        return new User(
                userModel.getId(),
                userModel.getName(),
                userModel.getEmail()
        );
    }
}
