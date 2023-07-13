package com.music.musicStreamer.core.utils.validators;

import com.music.musicStreamer.api.v1.models.MusicModel;
import com.music.musicStreamer.entities.music.MusicRequest;
import com.music.musicStreamer.enumerators.MusicMessages;
import com.music.musicStreamer.exceptions.MusicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MusicValidator {
    public void validateMusic(MusicRequest musicRequest) {
        if(musicRequest.getName().isBlank()) throw new MusicException(MusicMessages.NAME_REQUIRED);
        if(musicRequest.getArtist().isBlank()) throw new MusicException(MusicMessages.ARTIST_REQUIRED);
        if(musicRequest.getAlbum().isBlank()) throw new MusicException(MusicMessages.ALBUM_REQUIRED);
        if(musicRequest.getGenre().isBlank()) throw new MusicException(MusicMessages.GENRE_REQUIRED);
        if(musicRequest.getMusic().length == 0 || musicRequest.getMusic() == null) throw new MusicException(MusicMessages.MUSIC_REQUIRED);
    }

    public void validateIfImageIsNotNull(MusicModel musicModel) {
        if (musicModel == null) throw new MusicException(MusicMessages.NOT_FOUND);
    }
}