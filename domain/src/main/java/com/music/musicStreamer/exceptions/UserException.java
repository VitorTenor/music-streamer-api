package com.music.musicStreamer.exceptions;

import com.music.musicStreamer.enums.UserMessages;

public class UserException extends RuntimeException {
    public UserException(UserMessages message) {
        super(message.getMessage());
    }
}
