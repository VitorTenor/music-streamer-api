package com.music.musicStreamer.api.v1.models;

import com.music.musicStreamer.entities.image.Image;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Setter
@Getter
@Builder
@Table(name = "images")
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "music_id", nullable = false,length = 100)
    private int musicId;
    @Column(name = "path_name", nullable = false, length = 100)
    private String pathName;
    @Column(nullable = false, length = 100)
    private Date created_at;
    @Column(nullable = false, length = 100)
    private Date updated_at;

    public ImageModel() {
    }

    public Image toEntity(String url) {
        return  new Image( id,  pathName, url + pathName);
    }
}
