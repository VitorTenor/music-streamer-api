package com.music.musicStreamer.api.v1.models.dtos;

import com.music.musicStreamer.entities.user.UserAuthRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserLoginDTO {
    private String email;
    private String password;

    public UserAuthRequest toEntity() {
        return new UserAuthRequest(email, password);
    }
}
