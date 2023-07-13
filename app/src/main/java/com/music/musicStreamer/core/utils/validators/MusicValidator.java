package com.music.musicStreamer.core.utils.validators;

import com.music.musicStreamer.entities.music.MusicRequest;
import com.music.musicStreamer.exceptions.MusicException;

public class MusicValidator {
    public void validateMusic(MusicRequest musicRequest) {
        if(musicRequest.getName().isBlank()) throw new MusicException("Name is required");
        if(musicRequest.getArtist().isBlank()) throw new MusicException("Artist is required");
        if(musicRequest.getAlbum().isBlank()) throw new MusicException("Album is required");
        if(musicRequest.getGenre().isBlank()) throw new MusicException("Genre is required");
        if(musicRequest.getMusic().length == 0 || musicRequest.getMusic() == null) throw new MusicException("Music is required");
    }
}
