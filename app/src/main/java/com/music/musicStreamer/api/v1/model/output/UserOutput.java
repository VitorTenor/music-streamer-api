package com.music.musicStreamer.api.v1.model.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "UserRegisterResponse", description = "Response of the user register")
public class UserOutput {
    @Schema(description = "User name", example = "Vitor")
    private String name;
    @Schema(description = "User email", example = "vitor@vitor.com")
    private String email;
}

