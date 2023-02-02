package com.music.musicStreamer.domain.models;

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
    @Column(name = "playlist_id",nullable = false, length = 100)
    private int playlistId;

    @Column(name = "user_id", nullable = false, length = 100)
    private int userId;

    @Column(name = "music_id", nullable = false, length = 100)
    private int musicId;
    @Column(nullable = false, length = 100)
    private Date created_at;
    @Column(nullable = false, length = 100)
    private Date updated_at;


}
