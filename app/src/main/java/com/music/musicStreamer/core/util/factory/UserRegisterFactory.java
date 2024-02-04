package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.database.model.UserModel;
import com.music.musicStreamer.entity.user.UserRegisterResponseEntity;
import com.music.musicStreamer.entity.user.UserRegisterRequestEntity;
import com.music.musicStreamer.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserRegisterFactory {

    public UserModel toModel(UserRegisterRequestEntity user) {
        var userModel = new UserModel();
        userModel.setName(user.name());
        userModel.setEmail(user.email());
        userModel.setPassword(new BCryptPasswordEncoder().encode(user.password()));
        userModel.setCreated_at(new Date());
        userModel.setUpdated_at(new Date());
        userModel.setActive(true);
        userModel.setRole(UserRole.ADMIN);
        return userModel;
    }

    public UserRegisterResponseEntity toEntity(final UserModel userModel) {
        return new UserRegisterResponseEntity(userModel.getName(), userModel.getEmail());
    }
}
