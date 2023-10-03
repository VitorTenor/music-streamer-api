package com.music.musicStreamer.core.storage.impl;

import com.music.musicStreamer.core.storage.FilesBase;
import com.music.musicStreamer.entity.image.ImageRequest;
import com.music.musicStreamer.enums.ImageMessages;
import com.music.musicStreamer.exception.ImageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class ImageFiles implements FilesBase<ImageRequest> {
    private @Value("${storage.image.mediaType}") String IMAGE_TYPE;
    private @Value("${storage.image.path}") String IMAGE_PATH;


    @Override
    public void saveInFiles(ImageRequest imageRequest, String fileName) {
        Path path = Paths.get(IMAGE_PATH + fileName + IMAGE_TYPE);
        try {
            Files.write(path, imageRequest.getImage());
        } catch (Exception e) {
            throw new ImageException(ImageMessages.SAVE_STORAGE_ERROR);
        }
    }

    @Override
    public void deleteInFiles(String fileName) {
        try {
            Files.delete(Paths.get(IMAGE_PATH + fileName));
        } catch (Exception e) {
            throw new ImageException(ImageMessages.DELETE_STORAGE_ERROR);
        }
    }

    @Override
    public byte[] getBytesInFiles(String fileName) {
        try {
            return Files.readAllBytes(Paths.get(IMAGE_PATH + fileName));
        } catch (Exception e) {
            throw new ImageException(ImageMessages.READ_ERROR);
        }
    }

    @Override
    public List<?> getAllInFiles() {
        return null;
    }

    @Override
    public File getFile(String fileName) {
        return new File(IMAGE_PATH + fileName);
    }

    @Override
    public InputStream getInFilesStream(String fileName) {
        return null;
    }
}
