package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.image.Image;
import com.music.musicStreamer.entity.image.ImageRequest;


public interface ImageGateway {
    public Image saveImage(ImageRequest imageRequest);
    public byte[] getImageByFileName(String imageName);

    public Image getImageById(int id);
    public Boolean deleteImageByMusicId(int id);

    public Image getImageByMusicId(int id);
}
