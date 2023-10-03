package com.music.musicStreamer.core.utils.validators;

import com.music.musicStreamer.api.v1.models.UserModel;
import com.music.musicStreamer.api.v1.repositories.UserRepository;
import com.music.musicStreamer.entities.user.UserAuthRequest;
import com.music.musicStreamer.entities.user.UserRequest;
import com.music.musicStreamer.enums.UserMessages;
import com.music.musicStreamer.exceptions.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(UserValidator.class.getName());
    private final String REGEX_EMAIL = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    public void validateUser(UserRequest userRequest) {
        logger.info("[UserValidator] Validate user");
        validateUserEmail(userRequest.getEmail());

        if (userRequest.getName().isBlank()) throw new UserException(UserMessages.NAME_IS_REQUIRED);
        if (userRequest.getPassword().isBlank()) throw new UserException(UserMessages.PASSWORD_IS_REQUIRED);
        if (userRequest.getPassword().length() < 6) throw new UserException(UserMessages.PASSWORD_IS_TOO_SHORT);
        if (userRepository.existsByEmail(userRequest.getEmail())) throw new UserException(UserMessages.USER_ALREADY_EXISTS);

        logger.info("[UserValidator] User is valid");
    }

    public UserModel validateUserLogin(UserAuthRequest userAuthRequest) {
        validateUserEmail(userAuthRequest.getEmail());
        if (userAuthRequest.getPassword().isBlank()) throw new UserException(UserMessages.PASSWORD_IS_REQUIRED);
        return userRepository.findByEmail(userAuthRequest.getEmail()).orElseThrow(() -> new UserException(UserMessages.NOT_FOUND));
    }

    public void validateUserEmail(String email) {
        logger.info("[UserValidator] Validate user email");
        if (email.isBlank()) throw new UserException(UserMessages.EMAIL_IS_REQUIRED);
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        if (!pattern.matcher(email).matches()) throw new UserException(UserMessages.EMAIL_IS_INVALID);
        logger.info("[UserValidator] User email is valid");
    }


    public void validateIfExistById(int id) {
        if (!userRepository.existsById(id)) throw new UserException(UserMessages.NOT_FOUND);
    }

}
