package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.model.output.AuthenticationOutput;
import com.music.musicStreamer.entity.user.AuthenticationEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAssembler implements Assembler<AuthenticationEntity, AuthenticationOutput> {
    @Override
    public AuthenticationOutput toOutput(AuthenticationEntity entity) {
        return AuthenticationOutput.builder()
                .name(entity.name())
                .email(entity.email())
                .token(entity.token())
                .build();
    }
}
