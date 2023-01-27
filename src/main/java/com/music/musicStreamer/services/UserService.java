package com.music.musicStreamer.services;

import com.music.musicStreamer.models.UserModel;
import com.music.musicStreamer.models.dtos.UserDTO;
import com.music.musicStreamer.models.dtos.UserLoginDTO;
import com.music.musicStreamer.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO registerUser(UserModel userModel) {
        UserModel newUser = saveUser(userModel);
        return toDto(newUser);
    }

    private UserDTO toDto(UserModel newUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(newUser.getId());
        userDTO.setName(newUser.getName());
        userDTO.setEmail(newUser.getEmail());
        return userDTO;
    }

    private UserModel saveUser(UserModel userModel) {
        userModel.setCreated_at(new Date());
        userModel.setUpdated_at(new Date());
        return userRepository.save(userModel);
    }

    public Object loginUser(UserLoginDTO userLoginDTO) {
        UserModel user = userRepository.findByEmail(userLoginDTO.getEmail());
        if (user.getPassword().equals(userLoginDTO.getPassword())) {
            return toDto(user);
        }
        return "Wrong password";
    }
}
