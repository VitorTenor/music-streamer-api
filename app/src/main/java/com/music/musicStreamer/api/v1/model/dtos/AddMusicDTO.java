package com.music.musicStreamer.api.v1.model.dtos;

import com.music.musicStreamer.entities.music.MusicRequest;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class AddMusicDTO {
    private String name;
    private String artist;
    private String album;
    private String genre;
    private MultipartFile music;
    public MusicRequest toEntity() throws Exception {
        return new MusicRequest(name, artist, album, genre, music.getBytes());
    }
}


