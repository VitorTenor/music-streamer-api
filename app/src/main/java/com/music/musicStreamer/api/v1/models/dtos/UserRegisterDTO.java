package com.music.musicStreamer.api.v1.models.dtos;

import com.music.musicStreamer.entities.user.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterDTO {
    private String name;
    private String email;
    private String password;
    public UserRequest toEntity() { return new UserRequest(name, email, password);}
}
