package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.music.MusicDownload;
import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.music.MusicRequest;

import java.util.List;

public interface MusicGateway {
    MusicEntity saveMusic(MusicRequest musicRequest);
    List<MusicEntity> getAllMusics();
    byte[] playMusic(int id);
    MusicDownload downloadMusic(int id);
    MusicEntity getMusicById(int id);
    String deleteMusicById(int id);
}
