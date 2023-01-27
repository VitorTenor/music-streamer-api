package com.music.musicStreamer.controllers;

import com.music.musicStreamer.models.UserModel;
import com.music.musicStreamer.models.dtos.UserDTO;
import com.music.musicStreamer.models.dtos.UserLoginDTO;
import com.music.musicStreamer.repositories.UserRepository;
import com.music.musicStreamer.services.UserService;
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

    @PostMapping("")
    public ResponseEntity<UserDTO> register(@RequestBody UserModel userModel) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(userModel));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO) {
        if (userRepository.findByEmail(userLoginDTO.getEmail()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(userLoginDTO));
    }
}
