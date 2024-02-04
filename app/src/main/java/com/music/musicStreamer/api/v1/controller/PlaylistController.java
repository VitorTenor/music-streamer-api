package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.assembler.CreatePlaylistAssembler;
import com.music.musicStreamer.api.v1.request.CreatePlaylistRequest;
import com.music.musicStreamer.api.v1.request.MusicPlaylistRegister;
import com.music.musicStreamer.api.v1.response.CreatePlaylistResponse;
import com.music.musicStreamer.usecase.playlist.AddMusicPlaylistUseCase;
import com.music.musicStreamer.usecase.playlist.CreatePlaylistUseCase;
import com.music.musicStreamer.usecase.playlist.GetPlaylistByIdUseCase;
import com.music.musicStreamer.usecase.playlist.GetPlaylistByUserIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/playlists")
public class PlaylistController extends AbstractController {
    private final CreatePlaylistUseCase createPlaylistUseCase;
    private final GetPlaylistByIdUseCase getPlaylistByIdUseCase;
    private final AddMusicPlaylistUseCase addMusicPlaylistUseCase;
    private final GetPlaylistByUserIdUseCase getPlaylistByUserIdUseCase;
    /*
     * - Assembler to convert the response from the use case to the response of the API
     */
    private final CreatePlaylistAssembler createPlaylistAssembler;

    @PostMapping
    public ResponseEntity<CreatePlaylistResponse> create(@RequestBody CreatePlaylistRequest request) {
        info(this.getClass(), "Create playlist");
        info(this.getClass(), "Playlist name: " + request.name());

        final var response = createPlaylistAssembler.toResponse(
                createPlaylistUseCase.execute(request.toEntity(getUserFromToken().getUserId()))
        );

        return buildResponseEntity(HttpStatus.CREATED, response);
    }

    @PostMapping("/musics")
    public ResponseEntity<String> addMusic(@RequestBody MusicPlaylistRegister musicPlaylistRegister) {
        info(this.getClass(), "Add song to playlist");
        info(this.getClass(), "Playlist id: " + musicPlaylistRegister.playlistId());
        info(this.getClass(), "Music id: " + musicPlaylistRegister.musicId());


        return ResponseEntity.status(HttpStatus.OK)
                .body(addMusicPlaylistUseCase.execute(
                        musicPlaylistRegister.toEntity(getUserFromToken().getUserId())
                ));
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
