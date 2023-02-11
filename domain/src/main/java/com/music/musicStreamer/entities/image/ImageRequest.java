package com.music.musicStreamer.entities.image;

import java.util.Arrays;

public class ImageRequest {

    public ImageRequest(byte[] image, int id) {
        this.image = image;
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    private final byte[] image;
    private final int id;

}
