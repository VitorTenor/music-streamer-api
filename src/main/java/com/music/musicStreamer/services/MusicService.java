package com.music.musicStreamer.services;

import com.music.musicStreamer.models.MusicModel;
import com.music.musicStreamer.models.dtos.InputStreamDTO;
import com.music.musicStreamer.models.dtos.MusicDTO;
import com.music.musicStreamer.repositories.MusicRepository;
import com.music.musicStreamer.utils.GenerateName;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MusicService {

    private final MusicRepository musicRepository;
    private final PlaylistMusicService playlistMusicService;
    private final GenerateName generateName;
    private final ImageService imageService;

    public MusicService(MusicRepository musicRepository, PlaylistMusicService playlistMusicService, GenerateName generateName, ImageService imageService) {
        this.musicRepository = musicRepository;
        this.playlistMusicService = playlistMusicService;
        this.generateName = generateName;
        this.imageService = imageService;
    }

    public ResponseEntity uploadMusic(MultipartFile music, MusicModel musicModel) throws IOException {
        String newFileName = generateName.randomName();
        byte[] bytes = music.getBytes();
        Path path = Path.of("src/main/resources/media/musics/" + newFileName + ".mp3");
        Files.write(path, bytes);
        musicModel.setPath_name(newFileName);
        musicModel.setCreated_at(new Date());
        musicModel.setUpdated_at(new Date());
        if (musicRepository.save(musicModel) != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Music uploaded successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Music upload failed");
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
        file.delete();
        playlistMusicService.deleteAllByMusicId(id);
        musicRepository.deleteById(id);
        return true;
    }

    public List<MusicDTO> getMusics() {
        List<MusicModel> musicModel = musicRepository.findAll();
        return listToDTO(musicModel);
    }

    private List<MusicDTO> listToDTO(List<MusicModel> musicModel) {
        List<MusicDTO> musicDTO = new ArrayList<>();
        for (MusicModel music : musicModel) {
            MusicDTO musicDTO1 = new MusicDTO();
            musicDTO1.setId(music.getId());
            musicDTO1.setName(music.getName());
            musicDTO1.setArtist(music.getArtist());
            musicDTO1.setAlbum(music.getAlbum());
            musicDTO1.setGenre(music.getGenre());
            musicDTO1.setPath_name(music.getPath_name());
            musicDTO1.setImage(imageService.getImageByMusicId(music.getId()));
            musicDTO.add(musicDTO1);
        }
        return musicDTO;
    }

    public MusicDTO getMusicById(Integer id) {
        MusicModel musicModel = musicRepository.findById(id).orElse(null);
        if (musicModel == null) {
            return null;
        }
        return toMusiclDTO(musicModel);
    }

    private MusicDTO toMusiclDTO(MusicModel musicModel) {
        MusicDTO musicDTO = new MusicDTO();
        musicDTO.setId(musicModel.getId());
        musicDTO.setName(musicModel.getName());
        musicDTO.setArtist(musicModel.getArtist());
        musicDTO.setAlbum(musicModel.getAlbum());
        musicDTO.setGenre(musicModel.getGenre());
        musicDTO.setPath_name(musicModel.getPath_name());
        musicDTO.setImage(imageService.getImageByMusicId(musicModel.getId()));

        return musicDTO;
    }

    public InputStreamDTO getInputStream (MusicModel musicModel) throws IOException {
        File file = new File("src/main/resources/media/musics/" + musicModel.getPath_name() + ".mp3");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return new InputStreamDTO(resource, file);
    }
}


