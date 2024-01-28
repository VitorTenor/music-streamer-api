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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController extends AbstractController implements UserControllerOpenApi  {
    private final LoginUserUseCase loginUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    /*
    * - Assembler to convert the response from the use case to the response of the API
    */
    private final UserLoginAssembler userLoginAssembler;
    private final UserRegisterAssembler userRegisterAssembler;

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest request) {
        LOGGER.info("[UserController] Create user");
        LOGGER.info("[UserController] User email => " + request.email());

        var response = userRegisterAssembler.toResponse(createUserUseCase.execute(request.toEntity()));

        return buildResponseEntity(HttpStatus.CREATED, response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLogin request) {
        LOGGER.info("[UserController] Login user");
        LOGGER.info("[UserController] User email:" + request.email());

        var response = userLoginAssembler.toResponse(loginUserUseCase.execute(request.toEntity()));

        return buildResponseEntity(HttpStatus.OK, response);
    }
}
