package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.response.UserRegisterResponse;
import com.music.musicStreamer.entity.user.UserRegisterEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterAssembler implements Assembler<UserRegisterEntity, UserRegisterResponse> {
    @Override
    public UserRegisterResponse toResponse(UserRegisterEntity entity) {
        return UserRegisterResponse.builder()
                .name(entity.name())
                .email(entity.email())
                .build();
    }
}
