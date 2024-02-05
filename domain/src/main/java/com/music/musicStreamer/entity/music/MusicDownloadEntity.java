package com.music.musicStreamer.entity.music;

import java.io.File;
import java.io.InputStream;

public record MusicDownloadEntity(InputStream inputStream, File file) {

}
