package com.music.musicStreamer.exceptions;

import com.music.musicStreamer.enumerators.ImageMessages;

public class ImageException extends RuntimeException {
    public ImageException(ImageMessages message) {
        super(message.getMessage());
    }
}
