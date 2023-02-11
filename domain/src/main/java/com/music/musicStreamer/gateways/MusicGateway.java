package com.music.musicStreamer.gateways;

import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.music.MusicDownload;
import com.music.musicStreamer.entities.music.MusicRequest;

import java.io.IOException;
import java.util.List;

public interface MusicGateway {
    public Music saveMusic(MusicRequest musicRequest);
    public List<Music> getAllMusics();
    public Object playMusic(int id) throws IOException;
    public MusicDownload downloadMusic(int id) throws IOException;
    public Music getMusicById(int id);
    public String deleteMusicById(int id);
}
