package com.music.musicStreamer.core.storage;

import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface FilesBase<T> {
    void saveInFiles(T object, String fileName);
    void deleteInFiles(String fileName);
    byte[] getBytesInFiles(String fileName);
    List<?> getAllInFiles();
    File getFile(String fileName);
    InputStream getInFilesStream(String fileName);
}
