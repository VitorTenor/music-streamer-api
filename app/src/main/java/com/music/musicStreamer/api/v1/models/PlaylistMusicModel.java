package com.music.musicStreamer.api.v1.models;

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

    public PlaylistMusicModel(int playlistId, int userId, int musicId, Date created_at, Date updated_at) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.musicId = musicId;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

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


    public PlaylistMusicModel() {

    }
}
