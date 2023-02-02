package com.music.musicStreamer.domain.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PlaylistMusicDTO {
    private int playlist_id;
    private int user_id;
    private ArrayList<MusicDTO> musics;
}
