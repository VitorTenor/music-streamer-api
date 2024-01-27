package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.response.UserRegisterResponse;
import com.music.musicStreamer.entity.user.UserRegisterResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterAssembler implements Assembler<UserRegisterResponseEntity, UserRegisterResponse> {
    @Override
    public UserRegisterResponse toResponse(UserRegisterResponseEntity entity) {
        return UserRegisterResponse.builder()
                .name(entity.name())
                .email(entity.email())
                .build();
    }
}
