package com.music.musicStreamer.api.v1.openApi;

import com.music.musicStreamer.api.v1.model.input.CreateUserInput;
import com.music.musicStreamer.api.v1.model.input.LoginInput;
import com.music.musicStreamer.api.v1.model.output.AuthenticationOutput;
import com.music.musicStreamer.api.v1.model.output.UserOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@Tag(name = "User Controller")
public interface UserControllerOpenApi {
    @Operation(summary = "Create user",
            description = "Create user",
            tags = {"User Controller"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserOutput.class))),
            }
    )
    ResponseEntity<UserOutput> create(@RequestBody @Valid CreateUserInput createUserInput);

    @Operation(summary = "Login user",
            description = "Login user",
            tags = {"User Controller"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AuthenticationOutput.class))),
            }
    )
    ResponseEntity<AuthenticationOutput> login(@RequestBody @Valid LoginInput userRegisterRequest);

}
