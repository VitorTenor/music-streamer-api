package com.music.musicStreamer.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String role() {
        return this.role;
    }
}
