package com.music.musicStreamer.api.v1.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "images")
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "music_id", nullable = false, length = 100)
    private int musicId;
    @Column(name = "path_name", nullable = false, length = 100)
    private String pathName;
    @Column(nullable = false, length = 100)
    private Date created_at;
    @Column(nullable = false, length = 100)
    private Date updated_at;

    public ImageModel() {

    }

    public ImageModel(int musicId, String pathName, Date created_at, Date updated_at) {
        this.musicId = musicId;
        this.pathName = pathName;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
