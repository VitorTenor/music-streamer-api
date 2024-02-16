package com.music.musicStreamer.entity.user;

public class UserToken {
    private final String userEmail;
    private final int userId;
    
    public UserToken(String userEmail, int userId) {
        this.userEmail = userEmail;
        this.userId = userId;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public int getUserId() {
        return userId;
    }

    public static class builder {
        private String userEmail;
        private int userId;

        public builder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public UserToken build() {
            return new UserToken(userEmail, userId);
        }
    }
}

