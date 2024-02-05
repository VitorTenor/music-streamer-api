package com.music.musicStreamer.entity.image;

public record UploadImageEntity(
        byte[] image,
        int musicId
) {
}
