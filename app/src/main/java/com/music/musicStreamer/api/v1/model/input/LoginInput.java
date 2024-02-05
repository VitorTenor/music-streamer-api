package com.music.musicStreamer.api.v1.model.input;

import com.music.musicStreamer.entity.user.LoginEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public record LoginInput(
        @Email(message = "Email should be valid")
        @NotEmpty(message = "Email is required")
        String email,
        @NotEmpty(message = "Password is required")
        String password
) {
    public LoginEntity toEntity() {
        return new LoginEntity(email, password);
    }
}
