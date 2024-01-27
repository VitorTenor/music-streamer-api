package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.assembler.UserRegisterAssembler;
import com.music.musicStreamer.api.v1.request.UserLogin;
import com.music.musicStreamer.api.v1.openApi.UserControllerOpenApi;
import com.music.musicStreamer.api.v1.request.UserRegisterRequest;
import com.music.musicStreamer.api.v1.response.UserRegisterResponse;
import com.music.musicStreamer.entity.user.UserAuth;
import com.music.musicStreamer.usecase.user.CreateUserUseCase;
import com.music.musicStreamer.usecase.user.LoginUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController implements UserControllerOpenApi {
    private final LoginUserUseCase loginUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UserRegisterAssembler userRegisterAssembler;
    private final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest request) {
        LOGGER.info("[UserController] Create user");
        LOGGER.info("[UserController] User email => " + request.email());

        var response = userRegisterAssembler.toResponse(createUserUseCase.execute(request.toEntity()));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuth> login(@RequestBody @Valid UserLogin userLogin) {
        LOGGER.info("[UserController] Login user");
        LOGGER.info("[UserController] User email:" + userLogin.email());

        return ResponseEntity.status(HttpStatus.OK).body(loginUserUseCase.execute(userLogin.toEntity()));
    }
}
