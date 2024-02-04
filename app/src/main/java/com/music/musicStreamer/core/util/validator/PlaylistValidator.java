package com.music.musicStreamer.core.util.validator;

import com.music.musicStreamer.api.v1.database.repository.PlaylistRepository;
import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.enums.PlaylistMessages;
import com.music.musicStreamer.exception.PlaylistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class PlaylistValidator {
    private final PlaylistRepository playlistRepository;
    private final MusicValidator musicValidator;
    private final UserValidator userValidator;
    private final Logger LOGGER = Logger.getLogger(PlaylistValidator.class.getName());

    public void validateMusicPlaylist(MusicPlaylistRequest musicPlaylistRequest) {
        LOGGER.info("[PlaylistValidator] Validate music playlist");

        if (!playlistRepository.existsById(musicPlaylistRequest.getPlaylistId())) throw new PlaylistException(PlaylistMessages.NOT_FOUND);
        musicValidator.validateIfExistById(musicPlaylistRequest.getMusicId());
        userValidator.validateIfExistById(musicPlaylistRequest.getUserId());

        LOGGER.info("[PlaylistValidator] Music playlist validated");
    }
}
