package com.music.musicStreamer.entity.image;

public class Image {
    public Image(Integer id, String pathName, String url) {
        this.id = id;
        this.pathName = pathName;
        this.url = url;
    }

    private Integer id;
    private String pathName;
    private String url;
    public Integer getId() {
        return id;
    }

    public String getPathName() {
        return pathName;
    }

    public String getUrl() {
        return url;
    }

}
