package com.music.musicStreamer.api.v1.model.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "User Login Response", description = "Response of the user login")
public class UserLoginOutput {
    @Schema(description = "User name", example = "Vitor")
    private final String name;
    @Schema(description = "User email", example = "vitor@vitor.com")
    private final String email;
    @Schema(description = "User token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRvckB2aXRvci5jb20ifQ.3JvY2tldA")
    private final String token;
}
