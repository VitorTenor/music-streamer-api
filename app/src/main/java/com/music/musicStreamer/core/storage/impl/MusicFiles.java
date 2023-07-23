package com.music.musicStreamer.core.storage.impl;

import com.music.musicStreamer.api.v1.repositories.MusicRepository;
import com.music.musicStreamer.core.storage.FilesBase;
import com.music.musicStreamer.core.utils.factories.MusicFactory;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.music.MusicRequest;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.exceptions.MusicException;
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

@Component
@RequiredArgsConstructor
public class MusicFiles implements FilesBase<MusicRequest> {
    private @Value("${storage.music.mediaType}") String MUSIC_TYPE;
    private @Value("${storage.music.path}") String MUSIC_PATH;
    private final MusicFactory musicFactory;
    private final MusicRepository musicRepository;

    @Override
    public void saveInFiles(MusicRequest musicRequest, String fileName) {
        try {
            Path path = Path.of(MUSIC_PATH + fileName + MUSIC_TYPE);
            Files.write(path, musicRequest.getMusic());
        } catch (IOException e) {
            throw new MusicException(MusicMessages.SAVE_STORAGE_ERROR);
        }
    }

    @Override
    public void deleteInFiles(String fileName) {
        try {
            Files.delete(Path.of(MUSIC_PATH + fileName));
        } catch (Exception e) {
            throw new MusicException(MusicMessages.DELETE_STORAGE_ERROR);
        }
    }

    @Override
    public byte[] getBytesInFiles(String fileName) {
      return null;
    }

    @Override
    public List<Music> getAllInFiles() {
        try {
            return musicFactory.createMusicList(musicRepository.findAll());
        } catch (Exception e) {
            throw new MusicException(MusicMessages.GET_ERROR);
        }
    }
    @Override
    public InputStream getInFilesStream(String fileName) {
        try {
            File file = new File(MUSIC_PATH + fileName);
            return new InputStreamResource(new FileInputStream(file)).getInputStream();
        } catch (Exception e) {
            throw new MusicException(MusicMessages.PLAY_ERROR);
        }
    }
    @Override
    public File getFile(String fileName) {
        return new File(MUSIC_PATH + fileName);
    }
}
