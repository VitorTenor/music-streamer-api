package com.music.musicStreamer.enums;

public enum ImageMessages {
    NOT_FOUND("Image not found"),
    READ_ERROR("Error reading image"),
    DELETE_STORAGE_ERROR("Error deleting image in storage"),
    SAVE_STORAGE_ERROR("Error saving image in storage"),
    IMAGE_IS_REQUIRED("Image is required"),
    ID_IS_REQUIRED("Image id is required");

    public String message() {
        return this.message;
    }

    private final String message;

    ImageMessages(String message) {
        this.message = message;
    }
}
