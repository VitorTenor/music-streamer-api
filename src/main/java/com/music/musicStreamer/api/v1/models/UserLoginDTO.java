package com.music.musicStreamer.api.v1.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserLoginDTO {
    private String email;
    private String password;
}
