package com.music.musicStreamer.exception;

import com.music.musicStreamer.enums.PlaylistMessages;

public class PlaylistException extends RuntimeException {
    public PlaylistException(PlaylistMessages message) {
        super(message.getMessage());
    }
}
