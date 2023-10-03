package com.music.musicStreamer.exception;

import com.music.musicStreamer.enums.MusicMessages;

public class MusicException extends RuntimeException {
    public MusicException(MusicMessages message) {
        super(message.getMessage());
    }
}
