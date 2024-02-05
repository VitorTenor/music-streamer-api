package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.model.input.PlaylistMusicInput;
import com.music.musicStreamer.usecase.playlistMusic.CreatePlaylistMusicUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/playlist-music")
public class PlaylistMusicController extends AbstractController {
    private final CreatePlaylistMusicUseCase createPlaylistMusicUseCase;

    @PostMapping
    public ResponseEntity<?> create(PlaylistMusicInput input) {

        final var response = createPlaylistMusicUseCase.execute(input.toEntity((long) getUserFromToken().getUserId()));

        return buildResponseEntity(null, null);
    }
}
