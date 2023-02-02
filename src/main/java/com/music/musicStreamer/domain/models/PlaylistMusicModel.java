package com.music.musicStreamer.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name = "playlist_musics")
public class PlaylistMusicModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private int playlist_id;

    @Column(nullable = false, length = 100)
    private int user_id;

    @Column(nullable = false, length = 100)
    private int music_id;
    @Column(nullable = false, length = 100)
    private Date created_at;
    @Column(nullable = false, length = 100)
    private Date updated_at;


}
