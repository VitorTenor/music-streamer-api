package com.music.musicStreamer.core.utils.validators;

import com.music.musicStreamer.api.v1.repositories.UserRepository;
import com.music.musicStreamer.exceptions.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateIfExistById(int id) {
        if (!userRepository.existsById(id)) throw new UserException("User not found");
    }

}
