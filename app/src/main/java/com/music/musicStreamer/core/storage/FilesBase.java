package com.music.musicStreamer.core.storage;

public interface FilesBase<T> {
    void saveInFiles(T object, String fileName);
    void deleteInFiles(String fileName);

    byte[] getInFiles(String fileName);
}
