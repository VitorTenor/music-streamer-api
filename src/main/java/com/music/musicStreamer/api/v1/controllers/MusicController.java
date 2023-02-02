package com.music.musicStreamer.api.v1.controllers;

import com.music.musicStreamer.api.v1.models.MusicRegisterDTO;
import com.music.musicStreamer.domain.models.dtos.MusicDTO;
import com.music.musicStreamer.domain.services.MusicService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Upload music")
    @PostMapping("")
    public ResponseEntity<Object> uploadMusic(@RequestParam(name = "music")MultipartFile music,
                                              @RequestParam(name = "name") String name,
                                              @RequestParam(name = "artist") String artist,
                                              @RequestParam(name = "album") String album,
                                              @RequestParam(name = "genre") String genre) throws IOException {
        MusicRegisterDTO musicRegisterDTO = MusicRegisterDTO.builder()
                .name(name)
                .artist(artist)
                .album(album)
                .genre(genre)
                .music(music)
                .build();
        return musicService.uploadMusic(musicRegisterDTO);
    }

    @ApiOperation(value = "Get all musics")
    @GetMapping("")
    public ResponseEntity<Object> getMusics() {
        return ResponseEntity.status(HttpStatus.OK).body(musicService.getMusics());
    }

    @ApiOperation(value = "Get music")
    @GetMapping("/{id}")
    public ResponseEntity<MusicDTO> getMusic(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(musicService.getMusicById(id));
    }

    @ApiOperation(value = "Play music")
    @GetMapping("/play/{id}")
    public ResponseEntity<InputStreamResource> playMusicById(@PathVariable("id") Integer id) throws IOException {
        return musicService.playMusicById(id);
    }

    @ApiOperation(value = "Download music")
    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadMusicById(@PathVariable("id") Integer id) throws IOException {
        return musicService.downloadMusicById(id);
    }

    @ApiOperation(value = "Delete music")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMusicById(@PathVariable("id") Integer id) {
        if (musicService.deleteMusicById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Music deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Music not found");
        }
    }
}
