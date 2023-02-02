package com.music.musicStreamer.api.v1.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class MusicRegisterDTO {
    private String name;
    private String artist;
    private String album;
    private String genre;

    private MultipartFile music;
}
