package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.database.model.UserModel;
import com.music.musicStreamer.entity.user.CreateUserEntity;
import com.music.musicStreamer.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserRegisterFactory {

    public UserModel toModel(CreateUserEntity entity) {
        var userModel = new UserModel();
        userModel.setName(entity.name());
        userModel.setEmail(entity.email());
        userModel.setPassword(new BCryptPasswordEncoder().encode(entity.password()));
        userModel.setCreated_at(new Date());
        userModel.setUpdated_at(new Date());
        userModel.setActive(true);
        userModel.setRole(UserRole.ADMIN);
        return userModel;
    }

    public CreateUserEntity toEntity(final UserModel model) {
        return new CreateUserEntity(model.getName(), model.getEmail(), model.getPassword());
    }
}
