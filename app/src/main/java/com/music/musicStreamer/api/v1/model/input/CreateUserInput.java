package com.music.musicStreamer.api.v1.model.input;

import com.music.musicStreamer.entity.user.CreateUserEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record CreateUserInput(
        @NotEmpty(message = "Name is required")
        String name,
        @Email(message = "Email is invalid")
        @NotEmpty(message = "Email is required")
        String email,
        @Size(min = 6, message = "Password must be at least 6 characters")
        @NotEmpty(message = "Password is required")
        String password
) {
    public CreateUserEntity toEntity() {
        return new CreateUserEntity(name, email, password);
    }
}
