package com.music.musicStreamer.api.v1.model;

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
    @OneToOne
    @JoinColumn(name = "music_id", referencedColumnName = "id")
    private MusicModel music;
    @Column(name = "path_name", nullable = false, length = 100)
    private String pathName;
    @Column(nullable = false, length = 100)
    private Date created_at;
    @Column(nullable = false, length = 100)
    private Date updated_at;

    public ImageModel() {

    }

    public ImageModel(MusicModel music, String pathName, Date created_at, Date updated_at) {
        this.music = music;
        this.pathName = pathName;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
