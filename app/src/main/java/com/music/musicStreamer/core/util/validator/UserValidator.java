package com.music.musicStreamer.core.util.validator;

import com.music.musicStreamer.api.v1.model.UserModel;
import com.music.musicStreamer.api.v1.repository.UserRepository;
import com.music.musicStreamer.entity.user.UserAuthRequest;
import com.music.musicStreamer.entity.user.UserRegisterRequestEntity;
import com.music.musicStreamer.enums.UserMessages;
import com.music.musicStreamer.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;
    private final Logger LOGGER = Logger.getLogger(UserValidator.class.getName());
    private final String REGEX_EMAIL = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    public void validateUser(UserRegisterRequestEntity userRegisterRequestEntity) {
        LOGGER.info("[UserValidator] Validate user");
        validateUserEmail(userRegisterRequestEntity.email());

        if (userRegisterRequestEntity.name().isBlank()) throw new UserException(UserMessages.NAME_IS_REQUIRED);
        if (userRegisterRequestEntity.password().isBlank()) throw new UserException(UserMessages.PASSWORD_IS_REQUIRED);
        if (userRegisterRequestEntity.password().length() < 6) throw new UserException(UserMessages.PASSWORD_IS_TOO_SHORT);
        if (userRepository.existsByEmail(userRegisterRequestEntity.email())) throw new UserException(UserMessages.USER_ALREADY_EXISTS);

        LOGGER.info("[UserValidator] User is valid");
    }

    public UserModel validateUserLogin(UserAuthRequest userAuthRequest) {
        LOGGER.info("[UserValidator] Validate user login");

        validateUserEmail(userAuthRequest.getEmail());
        if (userAuthRequest.getPassword().isBlank()) throw new UserException(UserMessages.PASSWORD_IS_REQUIRED);
        return userRepository.findByEmail(userAuthRequest.getEmail()).orElseThrow(() -> new UserException(UserMessages.NOT_FOUND));
    }

    public void validateUserEmail(String email) {
        LOGGER.info("[UserValidator] Validate user email");

        if (email.isBlank()) throw new UserException(UserMessages.EMAIL_IS_REQUIRED);
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        if (!pattern.matcher(email).matches()) throw new UserException(UserMessages.EMAIL_IS_INVALID);

        LOGGER.info("[UserValidator] User email is valid");
    }


    public void validateIfExistById(int id) {
        LOGGER.info("[UserValidator] Validate if user exist by id");

        if (!userRepository.existsById(id)) throw new UserException(UserMessages.NOT_FOUND);

        LOGGER.info("[UserValidator] User exist by id");
    }

}
