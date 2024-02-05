package com.music.musicStreamer.core.storage.impl;

import com.music.musicStreamer.core.storage.FileBase;
import com.music.musicStreamer.entity.image.UploadImageEntity;
import com.music.musicStreamer.enums.ImageMessages;
import com.music.musicStreamer.exception.ImageException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
@RequiredArgsConstructor
public class ImageFile implements FileBase<UploadImageEntity> {
    private @Value("${storage.image.mediaType}") String IMAGE_TYPE;
    private @Value("${storage.image.path}") String IMAGE_PATH;
    private final Logger LOGGER = Logger.getLogger(ImageFile.class.getName());

    @Override
    public void saveInFiles(UploadImageEntity uploadImageEntity, final String fileName) {
        info(this.getClass(), "Save image in files");

        final var path = Paths.get(IMAGE_PATH + fileName + IMAGE_TYPE);
        info(this.getClass(), "Image path => " + path);

        try {
            Files.write(path, uploadImageEntity.image());
        } catch (Exception e) {
            info(this.getClass(), "Error => " + e.getMessage());
            throw new ImageException(ImageMessages.SAVE_STORAGE_ERROR);
        }
        info(this.getClass(), "Image saved");
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
        LOGGER.info("[ImageFiles] Get image by file name");
        LOGGER.info("[ImageFiles] File name => " + fileName);

        try {
            return Files.readAllBytes(Paths.get(IMAGE_PATH + fileName));
        } catch (Exception e) {
            LOGGER.info("[ImageFiles] Error => " + e.getMessage());

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
