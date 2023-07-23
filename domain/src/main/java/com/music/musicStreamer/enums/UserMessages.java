package com.music.musicStreamer.enums;

public enum UserMessages {
    NAME_IS_REQUIRED("Name is required"),
    PASSWORD_IS_REQUIRED("Password is required"),
    PASSWORD_IS_TOO_SHORT("Password must be at least 6 characters"),
    USER_ALREADY_EXISTS("User already exists"),
    NOT_FOUND("User not found"),
    EMAIL_IS_REQUIRED("Email is required"),
    EMAIL_IS_INVALID("Email is invalid");

    private final String message;

    public String getMessage() {
        return message;
    }

    UserMessages(String message) {
        this.message = message;
    }
}
