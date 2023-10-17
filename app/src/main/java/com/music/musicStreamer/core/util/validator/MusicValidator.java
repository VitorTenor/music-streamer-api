package com.music.musicStreamer.core.util.validator;

import com.music.musicStreamer.api.v1.model.MusicModel;
import com.music.musicStreamer.api.v1.repository.MusicRepository;
import com.music.musicStreamer.entity.music.MusicRequest;
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

    public void validateMusic(MusicRequest musicRequest) {
        LOGGER.info("[MusicValidator] Validate music");

        if (musicRequest.getName().isBlank()) throw new MusicException(MusicMessages.NAME_REQUIRED);
        if (musicRequest.getArtist().isBlank()) throw new MusicException(MusicMessages.ARTIST_REQUIRED);
        if (musicRequest.getAlbum().isBlank()) throw new MusicException(MusicMessages.ALBUM_REQUIRED);
        if (musicRequest.getGenre().isBlank()) throw new MusicException(MusicMessages.GENRE_REQUIRED);
        if (musicRequest.getMusic().length == 0 || musicRequest.getMusic() == null) throw new MusicException(MusicMessages.MUSIC_REQUIRED);

        LOGGER.info("[MusicValidator] Music is valid");
    }

    public void validateIfMusicIsNotNull(MusicModel musicModel) {
        LOGGER.info("[MusicValidator] Validate if music is not null");
        if (musicModel == null) throw new MusicException(MusicMessages.NOT_FOUND);

        LOGGER.info("[MusicValidator] Music is valid");
    }

    public void validateIfExistById(int id) {
        if (!musicRepository.existsById(id)) throw new MusicException(MusicMessages.NOT_FOUND);
    }
}
