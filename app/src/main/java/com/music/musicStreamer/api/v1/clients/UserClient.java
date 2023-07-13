package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.feign.Auth;
import com.music.musicStreamer.api.v1.repositories.UserRepository;
import com.music.musicStreamer.entities.user.User;
import com.music.musicStreamer.entities.user.UserAuth;
import com.music.musicStreamer.entities.user.UserAuthRequest;
import com.music.musicStreamer.entities.user.UserRequest;
import com.music.musicStreamer.exceptions.UserException;
import com.music.musicStreamer.gateways.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.music.musicStreamer.domain.models.UserModel;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;


@Component
@AllArgsConstructor
public class UserClient implements UserGateway {
    private final UserRepository userRepository;
    private final Auth auth;
    private final String REGEX_EMAIL = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    @Override
    @Transactional
    public User createUser(UserRequest userRequest) {
        validateUser(userRequest);
        UserModel createdUser = this.userRepository.save(toModel(userRequest));
        return createdUser.toEntity();
    }
    @Override
    public UserAuth loginUser(UserAuthRequest userAuthRequest) {
        UserModel user = validateUserLogin(userAuthRequest);
        try {
            return new UserAuth(user.getId(), user.getName(),user.getEmail(), auth.getToken(userAuthRequest));
        } catch (Exception e) {
            throw new UserException("Invalid credentials");
        }
    }
    public UserModel toModel(UserRequest userRequest) {
        UserModel userModel = new UserModel();
        userModel.setName(userRequest.getName());
        userModel.setEmail(userRequest.getEmail());
        userModel.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        userModel.setCreated_at(new Date());
        userModel.setUpdated_at(new Date());
        return userModel;
    }
    private void validateUser(UserRequest userRequest) {
        validateUserEmail(userRequest.getEmail());
        if (userRequest.getName().isBlank()) throw new UserException("Name is required");
        if (userRequest.getPassword().isBlank()) throw new UserException("Password is required");
        if (userRequest.getPassword().length() < 6) throw new UserException("Password must be at least 6 characters");
        Optional<UserModel> user = this.userRepository.findByEmail(userRequest.getEmail());
        if (user.isPresent())  throw new UserException("User already exists");
    }
    private UserModel validateUserLogin(UserAuthRequest userAuthRequest) {
        validateUserEmail(userAuthRequest.getEmail());
        if (userAuthRequest.getPassword().isBlank()) throw new UserException("Password is required");
        return this.userRepository.findByEmail(userAuthRequest.getEmail()).orElseThrow(() -> new UserException("User not found"));
    }
    private void validateUserEmail(String email) {
        if (email.isBlank()) throw new UserException("Email is required");
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        if (!pattern.matcher(email).matches()) throw new UserException("Email is invalid");
    }
}
