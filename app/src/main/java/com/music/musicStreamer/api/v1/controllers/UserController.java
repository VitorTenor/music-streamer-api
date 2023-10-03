package com.music.musicStreamer.api.v1.controllers;

import com.music.musicStreamer.api.v1.models.dtos.UserLoginDTO;
import com.music.musicStreamer.api.v1.openApi.UserControllerOpenApi;
import com.music.musicStreamer.api.v1.request.UserRegister;
import com.music.musicStreamer.entities.user.User;
import com.music.musicStreamer.entities.user.UserAuth;
import com.music.musicStreamer.usecases.user.CreateUserUseCase;
import com.music.musicStreamer.usecases.user.LoginUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music-streamer/v1/users")
public class UserController implements UserControllerOpenApi {
    private final LoginUserUseCase loginUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final Logger logger = Logger.getLogger(UserController.class.getName());

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegister userRegister) {
        logger.info("[UserController] Create user");
        logger.info("[UserController] User email => " + userRegister.email());
        return ResponseEntity.status(HttpStatus.OK).body(createUserUseCase.execute(userRegister.toEntity()));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuth> login(@RequestBody UserLoginDTO userLogin) {
        logger.info("Login user");
        logger.info("User email:" + userLogin.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(loginUserUseCase.execute(userLogin.toEntity()));
    }
}
