package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.assembler.UserLoginAssembler;
import com.music.musicStreamer.api.v1.assembler.UserRegisterAssembler;
import com.music.musicStreamer.api.v1.openApi.UserControllerOpenApi;
import com.music.musicStreamer.api.v1.request.UserLogin;
import com.music.musicStreamer.api.v1.request.UserRegisterRequest;
import com.music.musicStreamer.api.v1.response.UserLoginResponse;
import com.music.musicStreamer.api.v1.response.UserRegisterResponse;
import com.music.musicStreamer.usecase.user.CreateUserUseCase;
import com.music.musicStreamer.usecase.user.LoginUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.logging.Logger;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController extends AbstractController implements UserControllerOpenApi  {
    private final LoginUserUseCase loginUserUseCase;
    private final CreateUserUseCase createUserUseCase;

    /*
    * - Assembler to convert the response from the use case to the response of the API
    */
    private final UserLoginAssembler userLoginAssembler;
    private final UserRegisterAssembler userRegisterAssembler;

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest request) {
        log.info("[UserController] Create user");
        log.info("[UserController] User email => " + request.email());

        var response = userRegisterAssembler.toResponse(createUserUseCase.execute(request.toEntity()));

        return buildResponseEntity(HttpStatus.CREATED, response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLogin request) {
        log.info("[UserController] Login user");
        log.info("[UserController] User email:" + request.email());

        var response = userLoginAssembler.toResponse(loginUserUseCase.execute(request.toEntity()));

        return buildResponseEntity(HttpStatus.OK, response);
    }
}
