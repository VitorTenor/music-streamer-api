package com.music.musicStreamer.api.v1.request;

import com.music.musicStreamer.entity.user.UserRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record UserRegister(
        @NotEmpty(message = "Name is required")
        String name,
        @Email(message = "Email is invalid")
        @NotEmpty(message = "Email is required")
        String email,

        @Size(min = 6, message = "Password must be at least 6 characters")
        @NotEmpty(message = "Password is required")
        String password
) {
    public UserRequest toEntity() {
        return new UserRequest(name, email, password);
    }
}
