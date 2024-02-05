package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.music.MusicDownload;
import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.music.SaveMusicEntity;

import java.util.List;

public interface MusicGateway {
    MusicEntity saveMusic(SaveMusicEntity saveMusicEntity);
    List<MusicEntity> getAllMusics();
    byte[] playMusic(int id);
    MusicDownload downloadMusic(int id);
    MusicEntity getMusicById(int id);
    String deleteMusicById(int id);
}
