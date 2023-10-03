package com.music.musicStreamer.entity.playlist;

public class MusicPlaylistRequest {
    public MusicPlaylistRequest(int playlistId, int musicId, int userId) {
        this.playlistId = playlistId;
        this.musicId = musicId;
        this.userId = userId;
    }


    private int playlistId;
    private int musicId;
    private int userId;

    public int getPlaylistId() {
        return playlistId;
    }

    public int getMusicId() {
        return musicId;
    }

    public int getUserId() {
        return userId;
    }

}
