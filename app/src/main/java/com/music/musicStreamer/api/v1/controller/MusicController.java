package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.request.MusicUpload;
import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.entity.music.MusicDownload;
import com.music.musicStreamer.usecase.music.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music-streamer/v1/music")
public class MusicController {

    private @Value("${storage.image.mediaType}") String MUSIC_TYPE;
    private final PlayMusicByIdUseCase playMusicByIdUseCase;
    private final GetMusicByIdUseCase getMusicByIdUseCase;
    private final UploadMusicUseCase uploadMusicUseCase;
    private final DownloadByIdMusicUseCase downloadByIdMusicUseCase;
    private final DeleteMusicByIdUseCase deleteMusicByIdUseCase;
    private final GetAllMusicsUseCase getAllMusicsUseCase;
    private final Logger LOGGER = Logger.getLogger(MusicController.class.getName());

    @PostMapping("/upload")
    public ResponseEntity<Music> uploadMusic(@ModelAttribute MusicUpload musicUpload) throws Exception {
        LOGGER.info("[MusicController] Upload music");

        return ResponseEntity.status(HttpStatus.OK).body(uploadMusicUseCase.execute(musicUpload.toEntity()));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getMusics() {
        LOGGER.info("[MusicController] Get all musics");

        return ResponseEntity.status(HttpStatus.OK).body(getAllMusicsUseCase.execute());
    }

    @GetMapping("/getMusic/{musicId}")
    public ResponseEntity<Music> getMusic(@PathVariable(value = "musicId", required = true) Integer musicId) {
        LOGGER.info("[MusicController] Get music by id");
        LOGGER.info("[MusicController] Music id: " + musicId);

        return ResponseEntity.status(HttpStatus.OK).body(getMusicByIdUseCase.execute(musicId));
    }

    @GetMapping("/play/{musicId}")
    public ResponseEntity<byte[]> playMusicById(@PathVariable("musicId") Integer musicId) throws IOException {
        LOGGER.info("[MusicController] Play music by id");
        LOGGER.info("[MusicController] Music id: " + musicId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(playMusicByIdUseCase.execute(musicId));
    }

    @DeleteMapping("/delete/{musicId}")
    public ResponseEntity<Object> deleteMusicById(@PathVariable("musicId") Integer musicId) {
        LOGGER.info("[MusicController] Delete music by id");

        return ResponseEntity.status(HttpStatus.OK).body(deleteMusicByIdUseCase.execute(musicId));
    }
}
