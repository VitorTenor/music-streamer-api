package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.image.ImageEntity;
import com.music.musicStreamer.entity.image.UploadImageEntity;


public interface ImageGateway {
    ImageEntity save(UploadImageEntity uploadImageEntity);
    byte[] getByFileName(final String imageName);
    Boolean delete(final int id);
    ImageEntity getByMusicId(final int id);
}
