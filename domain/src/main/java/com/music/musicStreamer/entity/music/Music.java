package com.music.musicStreamer.entity.music;

import com.music.musicStreamer.entity.image.ImageEntity;

public class Music {
    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String genre;
    private String pathName;
    private ImageEntity imageEntity;

    public Music(int id, String name, String artist, String album, String genre, ImageEntity imageEntity, String pathName) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.imageEntity = imageEntity;
        this.pathName = pathName;
    }

    public Music() {

    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public String getPathName() {
        return pathName;
    }

    public ImageEntity getImage() {
        return imageEntity;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public void setImage(ImageEntity imageEntity) {
        this.imageEntity = imageEntity;
    }

}
