package com.music.musicStreamer.core.storage.impl;

import com.music.musicStreamer.api.v1.repository.MusicRepository;
import com.music.musicStreamer.core.storage.FilesBase;
import com.music.musicStreamer.core.util.factory.MusicFactory;
import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.entity.music.MusicRequest;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.exception.MusicException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class MusicFiles implements FilesBase<MusicRequest> {
    private @Value("${storage.music.mediaType}") String MUSIC_TYPE;
    private @Value("${storage.music.path}") String MUSIC_PATH;
    private final MusicFactory musicFactory;
    private final MusicRepository musicRepository;
    private final Logger LOGGER = Logger.getLogger(MusicFiles.class.getName());

    @Override
    public void saveInFiles(MusicRequest musicRequest, String fileName) {
        LOGGER.info("[MusicFiles] Save music in files");

        Path path = Path.of(MUSIC_PATH + fileName + MUSIC_TYPE);

        LOGGER.info("[MusicFiles] Path => " + path);

        try {
            Files.write(path, musicRequest.getMusic());
        } catch (IOException e) {
            LOGGER.info("[MusicFiles] Error to save music in files => " + e.getMessage());

            throw new MusicException(MusicMessages.SAVE_STORAGE_ERROR);
        }

        LOGGER.info("[MusicFiles] Music saved in files");
    }

    @Override
    public void deleteInFiles(String fileName) {
        LOGGER.info("[MusicFiles] Delete music in files");
        LOGGER.info("[MusicFiles] File name => " + fileName);

        try {
            Files.delete(Path.of(MUSIC_PATH + fileName));
        } catch (Exception e) {
            LOGGER.info("[MusicFiles] Error to delete music in files => " + e.getMessage());
            throw new MusicException(MusicMessages.DELETE_STORAGE_ERROR);
        }

        LOGGER.info("[MusicFiles] Music deleted in files");
    }

    @Override
    public byte[] getBytesInFiles(String fileName) {
      return null;
    }

    @Override
    public List<Music> getAllInFiles() {
        LOGGER.info("[MusicFiles] Get all musics in files");
        try {
            return musicFactory.createMusicList(musicRepository.findAll());
        } catch (Exception e) {
            LOGGER.info("[MusicFiles] Error to get all musics in files => " + e.getMessage());

            throw new MusicException(MusicMessages.GET_ERROR);
        }
    }
    @Override
    public InputStream getInFilesStream(String fileName) {
        LOGGER.info("[MusicFiles] Get music in files stream");

        File file = new File(MUSIC_PATH + fileName);

        LOGGER.info("[MusicFiles] File => " + file);
        try {
            return new InputStreamResource(new FileInputStream(file)).getInputStream();
        } catch (Exception e) {
            LOGGER.info("[MusicFiles] Error to get music in files stream => " + e.getMessage());

            throw new MusicException(MusicMessages.PLAY_ERROR);
        }
    }
    @Override
    public File getFile(String fileName) {
        LOGGER.info("[MusicFiles] Create file");
        LOGGER.info("[MusicFiles] File name => " + fileName);

        return new File(MUSIC_PATH + fileName);
    }
}
