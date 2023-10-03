package com.music.musicStreamer.exception;

import com.music.musicStreamer.enums.UserMessages;

public class UserException extends RuntimeException {
    public UserException(UserMessages message) {
        super(message.getMessage());
    }
}
