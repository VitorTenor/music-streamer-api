package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.database.model.UserModel;
import com.music.musicStreamer.entity.user.AuthenticationEntity;
import com.music.musicStreamer.entity.user.CreateUserEntity;
import com.music.musicStreamer.entity.user.UserEntity;
import com.music.musicStreamer.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserFactory {

    public UserModel toModel(CreateUserEntity entity) {
        var date = new Date();
        var password = new BCryptPasswordEncoder().encode(entity.password());

        var userModel = new UserModel();
        userModel.setName(entity.name());
        userModel.setEmail(entity.email());
        userModel.setPassword(password);
        userModel.setCreated_at(date);
        userModel.setUpdated_at(date);
        userModel.setActive(true);
        userModel.setRole(UserRole.ADMIN);
        return userModel;
    }

    public UserEntity toEntity(final UserModel model) {
        return new UserEntity(model.getName(), model.getEmail());
    }

    public AuthenticationEntity toEntity(final UserModel model, final String token) {
        return new AuthenticationEntity(model.getName(), model.getEmail(), token);
    }
}
