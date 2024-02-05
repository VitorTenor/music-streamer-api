package com.music.musicStreamer.core.storage;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface FileBase<T> {
    void saveInFiles(T object, String fileName);
    void deleteInFiles(String fileName);
    byte[] getBytesInFiles(String fileName);
    List<?> getAllInFiles();
    File getFile(String fileName);
    InputStream getInFilesStream(String fileName);
}
