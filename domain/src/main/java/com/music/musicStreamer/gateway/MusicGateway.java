package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.music.SaveMusicEntity;

import java.util.List;

public interface MusicGateway {
    MusicEntity saveMusic(SaveMusicEntity saveMusicEntity);
    List<MusicEntity> getAllMusics();
    byte[] playMusic(final Long musicId);
    MusicEntity getMusicById(final Long id);
    String deleteMusicById(final Long id);
}
