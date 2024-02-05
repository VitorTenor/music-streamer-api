package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.model.output.UserLoginOutput;
import com.music.musicStreamer.entity.user.AuthenticationEntity;
import org.springframework.stereotype.Component;

@Component
public class UserLoginAssembler implements Assembler<AuthenticationEntity, UserLoginOutput> {
    @Override
    public UserLoginOutput toOutput(AuthenticationEntity entity) {
        return UserLoginOutput.builder()
                .name(entity.name())
                .email(entity.email())
                .token(entity.token())
                .build();
    }
}
