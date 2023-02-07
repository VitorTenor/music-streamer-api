package com.music.musicStreamer.entities.image;

public class Image {
    public Image(Integer id, String path_name, String url) {
        this.id = id;
        this.path_name = path_name;
        this.url = url;
    }

    private Integer id;
    private String path_name;
    private String url;
    public Integer getId() {
        return id;
    }

    public String getPath_name() {
        return path_name;
    }

    public String getUrl() {
        return url;
    }

}
