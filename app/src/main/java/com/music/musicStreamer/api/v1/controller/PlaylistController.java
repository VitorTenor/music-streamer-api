package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.assembler.PlaylistAssembler;
import com.music.musicStreamer.api.v1.model.input.CreatePlaylistInput;
import com.music.musicStreamer.api.v1.model.output.PlaylistOutput;
import com.music.musicStreamer.usecase.playlist.CreatePlaylistUseCase;
import com.music.musicStreamer.usecase.playlist.GetPlaylistByIdUseCase;
import com.music.musicStreamer.usecase.playlist.GetPlaylistByUserIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/playlists")
public class PlaylistController extends AbstractController {
    /*
     * - Use case
     */
    private final CreatePlaylistUseCase createPlaylistUseCase;
    private final GetPlaylistByIdUseCase getPlaylistByIdUseCase;
    private final GetPlaylistByUserIdUseCase getPlaylistByUserIdUseCase;
    /*
     * - Assembler
     */
    private final PlaylistAssembler playlistAssembler;

    @PostMapping
    public ResponseEntity<PlaylistOutput> create(@RequestBody @Valid CreatePlaylistInput input) {
        info(this.getClass(), "Create playlist");
        info(this.getClass(), "Playlist name: " + input.name());


        final var response = playlistAssembler.toOutput(
                createPlaylistUseCase.execute(input.toEntity(getUserFromToken().getUserId()))
        );

        return buildResponseEntity(HttpStatus.CREATED, response);
    }

    @GetMapping("/getPlaylistById/{playlistId}")
    public ResponseEntity<Object> getPlaylistById(@PathVariable(value = "playlistId", required = true) int playlistId) {
        info(this.getClass(), "Get playlist by id");
        info(this.getClass(), "Playlist id: " + playlistId);

        return ResponseEntity.status(HttpStatus.OK).body(getPlaylistByIdUseCase.execute(playlistId));
    }

    @GetMapping("/getAllPlaylistsByUser")
    public ResponseEntity<Object> getAllPlaylistsByUserId() {
        info(this.getClass(), "Get all playlists by user id");

        return ResponseEntity.status(HttpStatus.OK)
                .body(getPlaylistByUserIdUseCase.execute(getUserFromToken().getUserId()));
    }

}
