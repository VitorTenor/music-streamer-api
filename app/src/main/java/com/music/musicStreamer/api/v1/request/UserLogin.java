package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entities.user.UserAuthRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public record UserLogin(
        @Email(message = "Email should be valid")
        @NotEmpty(message = "Email is required")
        String email,
        @NotEmpty(message = "Password is required")
        String password
) {
    public UserAuthRequest toEntity() {
        return new UserAuthRequest(email, password);
    }
}
