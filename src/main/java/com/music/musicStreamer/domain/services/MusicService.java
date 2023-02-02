package com.music.musicStreamer.domain.services;

import com.music.musicStreamer.api.v1.models.MusicRegisterDTO;
import com.music.musicStreamer.core.ToDTO;
import com.music.musicStreamer.core.ToModel;
import com.music.musicStreamer.domain.models.MusicModel;
import com.music.musicStreamer.domain.models.dtos.InputStreamDTO;
import com.music.musicStreamer.domain.models.dtos.MusicDTO;
import com.music.musicStreamer.domain.repositories.MusicRepository;
import com.music.musicStreamer.domain.utils.GenerateName;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class MusicService {

    private final MusicRepository musicRepository;
    private final PlaylistMusicService playlistMusicService;
    private final GenerateName generateName;
    private final ToModel toModel;
    private final ToDTO toDTO;

    public MusicService(MusicRepository musicRepository, PlaylistMusicService playlistMusicService, GenerateName generateName, ToModel toModel, ToDTO toDTO) {
        this.musicRepository = musicRepository;
        this.playlistMusicService = playlistMusicService;
        this.generateName = generateName;
        this.toModel = toModel;
        this.toDTO = toDTO;
    }

    public ResponseEntity<Object> uploadMusic(MusicRegisterDTO musicRegisterDTO) throws IOException {
        String newFileName = generateName.randomName();
        byte[] bytes = musicRegisterDTO.getMusic().getBytes();
        Path path = Path.of("src/main/resources/media/musics/" + newFileName + ".mp3");
        Files.write(path, bytes);
        MusicModel musicModel = toModel.toMusicModel(musicRegisterDTO);
        musicRepository.save(musicModel);
        return ResponseEntity.status(HttpStatus.OK).body("Music uploaded successfully");
    }

    public ResponseEntity<InputStreamResource> downloadMusicById(Integer id) throws IOException {
        MusicModel musicModel = musicRepository.findById(id).orElse(null);
        if (musicModel == null) {
            return ResponseEntity.notFound().build();
        }
        InputStreamDTO inputStream = getInputStream(musicModel);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + inputStream.getFile().getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(inputStream.getFile().length()).body(inputStream.getResource());
    }

    public ResponseEntity<InputStreamResource> playMusicById(Integer id) throws IOException {
        MusicModel musicModel = musicRepository.findById(id).orElse(null);
        if (musicModel == null) {
            return ResponseEntity.notFound().build();
        }
        InputStreamDTO inputStream = getInputStream(musicModel);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(inputStream.getResource());
    }

    public boolean deleteMusicById(Integer id) {
        MusicModel musicModel = musicRepository.findById(id).orElse(null);
        if (musicModel == null) {
            return false;
        }
        File file = new File("src/main/resources/media/musics/" + musicModel.getPath_name());
        playlistMusicService.deleteAllByMusicId(id);
        musicRepository.deleteById(id);
        return file.delete();
    }

    public List<MusicDTO> getMusics() {
        List<MusicModel> musicModel = musicRepository.findAll();
        return toDTO.listToMusicDTO(musicModel);
    }
    public MusicDTO getMusicById(Integer id) {
        MusicModel musicModel = musicRepository.findById(id).orElse(null);
        if (musicModel == null) {
            return null;
        }
        return toDTO.toMusiclDTO(musicModel);
    }
    public InputStreamDTO getInputStream (MusicModel musicModel) throws IOException {
        File file = new File("src/main/resources/media/musics/" + musicModel.getPath_name() + ".mp3");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return new InputStreamDTO(resource, file);
    }
}


