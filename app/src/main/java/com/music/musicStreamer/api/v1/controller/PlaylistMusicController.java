package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.model.input.PlaylistMusicInput;
import com.music.musicStreamer.usecase.playlistMusic.CreatePlaylistMusicUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/playlist-music")
public class PlaylistMusicController extends AbstractController {
    /*
     * - Use case
     */
    private final CreatePlaylistMusicUseCase createPlaylistMusicUseCase;

    @PostMapping
    public ResponseEntity<String> create(PlaylistMusicInput input) {
        info(this.getClass(), "Create playlist music");

        final var response = createPlaylistMusicUseCase.execute(input.toEntity((long) getUserFromToken().getUserId()));

        info(this.getClass(), "Playlist music created");
        return buildResponseEntity(HttpStatus.CREATED, response);
    }
}
