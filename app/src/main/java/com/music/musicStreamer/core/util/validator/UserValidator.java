package com.music.musicStreamer.core.util.validator;

import com.music.musicStreamer.api.v1.repository.UserRepository;
import com.music.musicStreamer.enums.UserMessages;
import com.music.musicStreamer.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void validateIfExistById(int id) {
        log.info("[UserValidator] Validate if user exist by id");

        if (!userRepository.existsById(id)) {
            throw new UserException(UserMessages.NOT_FOUND);
        }

        log.info("[UserValidator] User exist by id");
    }
}
