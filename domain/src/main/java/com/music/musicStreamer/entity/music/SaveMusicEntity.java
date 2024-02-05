package com.music.musicStreamer.entity.music;


public class SaveMusicEntity {

    public SaveMusicEntity(String name, String artist, String album, String genre, byte[] music) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.music = music;
    }
    private String name;
    private String artist;

    private String album;
    private String genre;
    private byte[] music;

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

    public byte[] getMusic() {
        return music;
    }

}
