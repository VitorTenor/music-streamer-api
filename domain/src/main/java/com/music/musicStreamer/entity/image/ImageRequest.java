package com.music.musicStreamer.entity.image;

public class ImageRequest {

    public ImageRequest(byte[] image, int musicId) {
        this.image = image;
        this.musicId = musicId;
    }

    public byte[] getImage() {
        return image;
    }

    public int getId() {
        return musicId;
    }

    private final byte[] image;
    private final int musicId;

}
