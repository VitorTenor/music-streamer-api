package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entity.user.UserRegisterRequestEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record UserRegisterRequest(
        @NotEmpty(message = "Name is required")
        String name,
        @Email(message = "Email is invalid")
        @NotEmpty(message = "Email is required")
        String email,
        @Size(min = 6, message = "Password must be at least 6 characters")
        @NotEmpty(message = "Password is required")
        String password
) {
    public UserRegisterRequestEntity toEntity() {
        return new UserRegisterRequestEntity(name, email, password);
    }
}
