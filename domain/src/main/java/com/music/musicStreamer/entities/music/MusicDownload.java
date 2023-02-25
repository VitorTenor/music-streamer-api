package com.music.musicStreamer.entities.music;

import java.io.File;
import java.io.InputStream;

public class MusicDownload {
    public MusicDownload(InputStream inputStream, File file) {
        this.inputStream = inputStream;
        this.file = file;
    }


    private final InputStream inputStream;
    private final File file;

    public InputStream getInputStream() {
        return inputStream;
    }

    public File getFile() {
        return file;
    }
}
