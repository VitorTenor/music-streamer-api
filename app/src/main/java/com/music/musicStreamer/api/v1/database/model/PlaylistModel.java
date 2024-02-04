package com.music.musicStreamer.api.v1.database.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "playlists")
public class PlaylistModel {
    public PlaylistModel(String name, int userId, Date created_at, Date updated_at) {
        this.name = name;
        this.userId = userId;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "user_id", nullable = false, length = 100)
    private int userId;

    @Column(nullable = false, length = 100)
    private Date created_at;

    @Column(nullable = false, length = 100)
    private Date updated_at;

    public PlaylistModel() {

    }
}
