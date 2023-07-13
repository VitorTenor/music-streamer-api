package com.music.musicStreamer.exceptions;

import com.music.musicStreamer.enumerators.PlaylistMessages;

public class PlaylistException extends RuntimeException {
    public PlaylistException(PlaylistMessages message) {
        super(message.getMessage());
    }
}
