package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.model.output.CreateUserOutput;
import com.music.musicStreamer.entity.user.CreateUserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterAssembler implements Assembler<CreateUserEntity, CreateUserOutput> {
    @Override
    public CreateUserOutput toOutput(CreateUserEntity entity) {
        return CreateUserOutput.builder()
                .name(entity.name())
                .email(entity.email())
                .build();
    }
}
