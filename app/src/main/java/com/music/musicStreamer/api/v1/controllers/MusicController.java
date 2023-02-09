package com.music.musicStreamer.api.v1.controllers;

import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.music.MusicDownload;
import com.music.musicStreamer.entities.music.MusicRequest;
import com.music.musicStreamer.usecases.music.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music-streamer/v1/musics")
public class MusicController {

    private @Value("${storage.image.mediaType}") String MUSIC_TYPE;
    private final PlayMusicByIdUseCase playMusicByIdUseCase;
    private final GetMusicByIdUseCase getMusicByIdUseCase;
    private final UploadMusicUseCase uploadMusicUseCase;
    private final DownloadByIdMusicUseCase downloadByIdMusicUseCase;
    private final DeleteMusicByIdUseCase deleteMusicByIdUseCase;
    private final GetAllMusicsUseCase getAllMusicsUseCase;

    @ApiOperation(value = "Upload music")
    @PostMapping("")
    public ResponseEntity<Object> uploadMusic(@RequestParam(name = "music")MultipartFile music,
                                              @RequestParam(name = "name") String name,
                                              @RequestParam(name = "artist") String artist,
                                              @RequestParam(name = "album") String album,
                                              @RequestParam(name = "genre") String genre) throws IOException {
        MusicRequest musicRequest = new MusicRequest(name, artist, album, genre, music.getBytes());
        return ResponseEntity.status(HttpStatus.OK).body(uploadMusicUseCase.execute(musicRequest));
    }

    @ApiOperation(value = "Get all musics")
    @GetMapping("")
    public ResponseEntity<Object> getMusics() {
        return ResponseEntity.status(HttpStatus.OK).body(getAllMusicsUseCase.execute());
    }

    @ApiOperation(value = "Get music")
    @GetMapping("/{id}")
    public ResponseEntity<Music> getMusic(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(getMusicByIdUseCase.execute(id));
    }

    @ApiOperation(value = "Play music")
    @GetMapping("/play/{id}")
    public ResponseEntity<Object> playMusicById(@PathVariable("id") Integer id) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(playMusicByIdUseCase.execute(id));
    }

    @ApiOperation(value = "Download music")
    @GetMapping("/download/{id}")
    public ResponseEntity downloadMusicById(@PathVariable("id") Integer id) throws Exception {
        MusicDownload music = downloadByIdMusicUseCase.execute(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + music.getFile().getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(music.getFile().length()).body(music.getInputStream() + MUSIC_TYPE);
    }

    @ApiOperation(value = "Delete music")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMusicById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(deleteMusicByIdUseCase.execute(id));
    }
}
