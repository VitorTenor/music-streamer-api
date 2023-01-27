package com.music.musicStreamer.models.dtos;

import com.music.musicStreamer.models.MusicModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicDTO {
    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String genre;
    private String path_name;

    private ImageDTO image;

}
