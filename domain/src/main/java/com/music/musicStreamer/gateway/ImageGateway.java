package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.image.ImageEntity;
import com.music.musicStreamer.entity.image.UploadImageEntity;


public interface ImageGateway {
    ImageEntity save(UploadImageEntity uploadImageEntity);
    byte[] getByFileName(final String pathName);
    Boolean deleteByMusicId(final Long musicId);
    ImageEntity getByMusicId(final int musicId);
}
