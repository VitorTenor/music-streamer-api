package com.music.musicStreamer.api.v1.controllers;

import com.music.musicStreamer.domain.models.dtos.UserDTO;
import com.music.musicStreamer.api.v1.models.UserLoginDTO;
import com.music.musicStreamer.api.v1.models.UserRegisterDTO;
import com.music.musicStreamer.domain.repositories.UserRepository;
import com.music.musicStreamer.domain.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Create user")
    @PostMapping("")
    public ResponseEntity<UserDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(userRegisterDTO));
    }

    @ApiOperation(value = "Login user")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO) {
        if(userRepository.findByEmailForAuthenticate(userLoginDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(userLoginDTO));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
