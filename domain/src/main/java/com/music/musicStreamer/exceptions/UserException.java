package com.music.musicStreamer.exceptions;

import com.music.musicStreamer.enumerators.UserMessages;

public class UserException extends RuntimeException {
    public UserException(UserMessages message) {
        super(message.getMessage());
    }
}
