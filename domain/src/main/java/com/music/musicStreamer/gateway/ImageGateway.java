package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.image.ImageEntity;
import com.music.musicStreamer.entity.image.UploadImageEntity;


public interface ImageGateway {
    ImageEntity save(UploadImageEntity uploadImageEntity);
    byte[] getImageByFileName(final String imageName);
    Boolean deleteByMusicId(final int id);
    ImageEntity getByMusicId(final int id);
}
