package com.music.musicStreamer.core.storage;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@Component
public interface FileBase<T> {
    void saveInFiles(T object, String fileName);
    void deleteInFiles(String fileName);
    byte[] getBytesInFiles(String fileName);
    List<?> getAllInFiles();
    File getFile(String fileName);
    InputStream getInFilesStream(String fileName);
}
