package com.music.musicStreamer.exceptions;

import com.music.musicStreamer.enums.ImageMessages;

public class ImageException extends RuntimeException {
    public ImageException(ImageMessages message) {
        super(message.getMessage());
    }
}
