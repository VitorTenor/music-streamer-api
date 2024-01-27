package com.music.musicStreamer.core.util.validator;

import com.music.musicStreamer.api.v1.repository.MusicRepository;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.exception.MusicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class MusicValidator {

    private final MusicRepository musicRepository;
    private final Logger LOGGER = Logger.getLogger(MusicValidator.class.getName());

    public void validateIfExistById(int id) {
        if (!musicRepository.existsById(id)) throw new MusicException(MusicMessages.NOT_FOUND);
    }
}
