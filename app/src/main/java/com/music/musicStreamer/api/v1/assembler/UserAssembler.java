package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.model.output.UserOutput;
import com.music.musicStreamer.entity.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler implements Assembler<UserEntity, UserOutput> {
    @Override
    public UserOutput toOutput(UserEntity entity) {
        return UserOutput.builder()
                .name(entity.name())
                .email(entity.email())
                .build();
    }
}
