package com.music.musicStreamer.exceptions;

import com.music.musicStreamer.enumerators.ImageErrorMessage;

public class ImageException extends RuntimeException {
    public ImageException(ImageErrorMessage message) {
        super(message.getMessage());
    }
}
