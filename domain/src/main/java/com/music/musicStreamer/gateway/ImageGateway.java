package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.image.ImageEntity;
import com.music.musicStreamer.entity.image.ImageRequest;


public interface ImageGateway {
    public ImageEntity saveImage(ImageRequest imageRequest);
    public byte[] getImageByFileName(String imageName);

    public ImageEntity getImageById(int id);
    public Boolean deleteImageByMusicId(int id);

    public ImageEntity getImageByMusicId(int id);
}
