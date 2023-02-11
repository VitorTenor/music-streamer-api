package com.music.musicStreamer.gateways;

import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.entities.image.ImageRequest;


public interface ImageGateway {
    public Image saveImage(ImageRequest imageRequest);
    public byte[] getImage(String imageName);

    public Image getImageById(int id);
    public Boolean deleteImageByMusicId(int id);

    public Image getImageByMusicId(int id);
}
