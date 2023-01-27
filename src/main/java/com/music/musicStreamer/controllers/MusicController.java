package com.music.musicStreamer.controllers;

import com.music.musicStreamer.models.MusicModel;
import com.music.musicStreamer.models.dtos.MusicDTO;
import com.music.musicStreamer.services.MusicService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/musics")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @PostMapping("")
    public ResponseEntity uploadMusic(@RequestParam(name = "music")MultipartFile music,
                                              @RequestParam(name = "name") String name,
                                              @RequestParam(name = "artist") String artist,
                                              @RequestParam(name = "album") String album,
                                              @RequestParam(name = "genre") String genre) throws IOException {
        MusicModel musicModel = new MusicModel();
            musicModel.setName(name);
            musicModel.setArtist(artist);
            musicModel.setAlbum(album);
            musicModel.setGenre(genre);
        return musicService.uploadMusic(music, musicModel);
    }

    @GetMapping("")
    public ResponseEntity<Object> getMusics() {
        return ResponseEntity.status(HttpStatus.OK).body(musicService.getMusics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicDTO> getMusic(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(musicService.getMusicById(id));
    }

    @GetMapping("/play/{id}")
    public ResponseEntity<InputStreamResource> playMusicById(@PathVariable("id") Integer id) throws IOException {
        return musicService.playMusicById(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadMusicById(@PathVariable("id") Integer id) throws IOException {
        return musicService.downloadMusicById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMusicById(@PathVariable("id") Integer id) {
        if (musicService.deleteMusicById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Music deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Music not found");
        }
    }
}
