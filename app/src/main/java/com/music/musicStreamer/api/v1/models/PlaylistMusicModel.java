package com.music.musicStreamer.api.v1.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    public PlaylistMusicModel(int playlistId, int userId, int musicId, Date date, Date date1) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.musicId = musicId;
        this.created_at = date;
        this.updated_at = date1;
    }

    public PlaylistMusicModel() {

    }
}
