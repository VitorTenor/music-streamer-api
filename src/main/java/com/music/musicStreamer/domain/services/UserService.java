package com.music.musicStreamer.domain.services;

import com.music.musicStreamer.core.ToDTO;
import com.music.musicStreamer.core.ToModel;
import com.music.musicStreamer.domain.models.feign.Auth;
import com.music.musicStreamer.domain.models.UserModel;
import com.music.musicStreamer.domain.models.dtos.UserDTO;
import com.music.musicStreamer.api.v1.models.UserLoginDTO;
import com.music.musicStreamer.api.v1.models.UserRegisterDTO;
import com.music.musicStreamer.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    Auth auth;

    private final ToModel toModel;
    private final ToDTO toDTO;
    private final UserRepository userRepository;

    public UserService(ToModel toModel, ToDTO toDTO, UserRepository userRepository) {
        this.toModel = toModel;
        this.toDTO = toDTO;
        this.userRepository = userRepository;
    }

    public UserDTO registerUser(UserRegisterDTO userRegisterDTO) {
        return toDTO.toUserDTO(saveUser(toModel.toUserModel(userRegisterDTO)));
    }

    private UserModel saveUser(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public Object loginUser(UserLoginDTO userLoginDTO) {
        return auth.getToken(userLoginDTO);
    }
}
