package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.entity.music.MusicDownload;
import com.music.musicStreamer.entity.music.MusicRequest;

import java.io.IOException;
import java.util.List;

public interface MusicGateway {
    Music saveMusic(MusicRequest musicRequest);
    List<Music> getAllMusics();
    byte[] playMusic(int id) throws IOException;
    MusicDownload downloadMusic(int id) throws IOException;
    Music getMusicById(int id);
    String deleteMusicById(int id);
}
