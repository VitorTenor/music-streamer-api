package com.music.musicStreamer.api.v1.controllers;

import com.music.musicStreamer.api.v1.models.dtos.UserLoginDTO;
import com.music.musicStreamer.api.v1.models.dtos.UserRegisterDTO;
import com.music.musicStreamer.entities.user.User;
import com.music.musicStreamer.entities.user.UserAuth;
import com.music.musicStreamer.usecases.user.CreateUserUseCase;
import com.music.musicStreamer.usecases.user.LoginUserUseCase;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music-streamer/v1/users")
public class UserController {
    private final LoginUserUseCase loginUserUseCase;
    private final CreateUserUseCase createUserUseCase;

    @ApiOperation(value = "Create user")
    @PostMapping("")
    public ResponseEntity<User> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(createUserUseCase.execute(userRegisterDTO.toEntity()));
    }

    @ApiOperation(value = "Login user")
    @PostMapping("/login")
    public ResponseEntity<UserAuth> login(@RequestBody UserLoginDTO userLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(loginUserUseCase.execute(userLogin.toEntity()));
    }
}
