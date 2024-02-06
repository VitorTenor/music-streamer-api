package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.assembler.MusicAssembler;
import com.music.musicStreamer.api.v1.model.input.MusicUploadInput;
import com.music.musicStreamer.api.v1.model.output.MusicOutput;
import com.music.musicStreamer.usecase.music.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/musics")
public class MusicController extends AbstractController {
    /*
     * UseCase
     */
    private final GetAllMusicsUseCase getAllUseCase;
    private final UploadMusicUseCase uploadMusicUseCase;
    private final GetMusicByIdUseCase getMusicByIdUseCase;
    private final PlayMusicByIdUseCase playMusicByIdUseCase;
    private final DeleteMusicByIdUseCase deleteMusicByIdUseCase;
    /*
     * Assembler
     */
    private final MusicAssembler musicAssembler;

    @PostMapping("/uploads")
    public ResponseEntity<MusicOutput> uploadMusic(@ModelAttribute MusicUploadInput input) {
        info(this.getClass(), "Upload music");

        final var response = musicAssembler.toOutput(
                uploadMusicUseCase.execute(input.toEntity())
        );
        info(this.getClass(), "Music uploaded");

        return buildResponseEntity(HttpStatus.CREATED, response);
    }

    @GetMapping
    public ResponseEntity<List<MusicOutput>> getAll() {
        info(this.getClass(), "Get all musics");

        final var response = musicAssembler.toOutputList(
                getAllUseCase.execute()
        );
        info(this.getClass(), "Musics found: " + response.size());

        return buildResponseEntity(HttpStatus.OK, response);
    }

    @GetMapping("/{musicId}")
    public ResponseEntity<MusicOutput> getMusic(@PathVariable(value = "musicId") final Long musicId) {
        info(this.getClass(), "Get music by id");
        info(this.getClass(), "Music id: " + musicId);

        final var response = musicAssembler.toOutput(
                getMusicByIdUseCase.execute(musicId.intValue())
        );
        info(this.getClass(), "Music found");

        return buildResponseEntity(HttpStatus.OK, response);
    }

    @GetMapping("/play/{musicId}")
    public ResponseEntity<byte[]> playById(@PathVariable("musicId") final Long musicId) {
        info(this.getClass(), "Play music by id");
        info(this.getClass(), "Music id: " + musicId);

        final var response = playMusicByIdUseCase.execute(musicId.intValue());
        info(this.getClass(), "Music played");

        return buildResponseEntity(HttpStatus.OK, MediaType.parseMediaType("audio/mpeg"), response);
    }

    @DeleteMapping("/{musicId}")
    public ResponseEntity<String> deleteById(@PathVariable("musicId") final Long musicId) {
        info(this.getClass(), "Delete music by id");

        final var response = deleteMusicByIdUseCase.execute(musicId);
        info(this.getClass(), "Music deleted");

        return buildResponseEntity(HttpStatus.OK, response);
    }
}
