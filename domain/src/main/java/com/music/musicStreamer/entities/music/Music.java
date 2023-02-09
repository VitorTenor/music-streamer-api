package com.music.musicStreamer.entities.music;

import com.music.musicStreamer.entities.image.Image;

public class Music {
    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String genre;
    private String path_name;
    private Image image;

    public Music(int id, String name, String artist, String album, String genre, Image image, String path_name) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.image = image;
        this.path_name = path_name;
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

    public String getPath_name() {
        return path_name;
    }

    public Image getImage() {
        return image;
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

    public void setPath_name(String path_name) {
        this.path_name = path_name;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
