package com.music.musicStreamer.exceptions;

import com.music.musicStreamer.enumerators.MusicMessages;

public class MusicException extends RuntimeException {
    public MusicException(MusicMessages message) {
        super(message.getMessage());
    }
}
