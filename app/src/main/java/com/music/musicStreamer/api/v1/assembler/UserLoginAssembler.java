package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.response.UserLoginResponse;
import com.music.musicStreamer.entity.user.UserLoginResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserLoginAssembler implements Assembler<UserLoginResponseEntity, UserLoginResponse> {
    @Override
    public UserLoginResponse toOutput(UserLoginResponseEntity entity) {
        return UserLoginResponse.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .token(entity.getToken())
                .build();
    }
}
