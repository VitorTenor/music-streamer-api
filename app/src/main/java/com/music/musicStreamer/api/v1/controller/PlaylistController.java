package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.request.MusicPlaylistRegister;
import com.music.musicStreamer.api.v1.request.PlaylistRegister;
import com.music.musicStreamer.entity.playlist.Playlist;
import com.music.musicStreamer.usecase.playlist.AddMusicPlaylistUseCase;
import com.music.musicStreamer.usecase.playlist.CreatePlaylistUseCase;
import com.music.musicStreamer.usecase.playlist.GetPlaylistByIdUseCase;
import com.music.musicStreamer.usecase.playlist.GetPlaylistByUserIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/playlist")
public class PlaylistController extends AbstractController {
    private final CreatePlaylistUseCase createPlaylistUseCase;
    private final GetPlaylistByIdUseCase getPlaylistByIdUseCase;
    private final AddMusicPlaylistUseCase addMusicPlaylistUseCase;
    private final GetPlaylistByUserIdUseCase getPlaylistByUserIdUseCase;
    private final Logger LOGGER = Logger.getLogger(PlaylistController.class.getName());

    @PostMapping("/createPlaylist")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistRegister playlistRegister) throws Exception {
        LOGGER.info("[PlaylistController] Create playlist");
        LOGGER.info("[PlaylistController] Playlist name: " + playlistRegister.name());

        return ResponseEntity.status(HttpStatus.OK)
                .body(createPlaylistUseCase.execute(
                            playlistRegister.toEntity(getUserFromToken().getUserId())
                ));
    }

    @PostMapping("/addSongToPlaylist")
    public ResponseEntity<String> addSongToPlaylist(@RequestBody MusicPlaylistRegister musicPlaylistRegister) {
        LOGGER.info("[PlaylistController] Add song to playlist");
        LOGGER.info("[PlaylistController] Playlist id: " + musicPlaylistRegister.playlistId());
        LOGGER.info("[PlaylistController] Music id: " + musicPlaylistRegister.musicId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(addMusicPlaylistUseCase.execute(
                        musicPlaylistRegister.toEntity(getUserFromToken().getUserId())
                ));
    }

    @GetMapping("/getPlaylistById/{playlistId}")
    public ResponseEntity<Object> getPlaylistById(@PathVariable(value = "playlistId", required = true) int playlistId) {
        LOGGER.info("[PlaylistController] Get playlist by id");
        LOGGER.info("[PlaylistController] Playlist id: " + playlistId);

        return ResponseEntity.status(HttpStatus.OK).body(getPlaylistByIdUseCase.execute(playlistId));
    }

    @GetMapping("/getAllPlaylistsByUser")
    public ResponseEntity<Object> getAllPlaylistsByUserId() {
        LOGGER.info("[PlaylistController] Get all playlists by user id");

        return ResponseEntity.status(HttpStatus.OK)
                .body(getPlaylistByUserIdUseCase.execute(getUserFromToken().getUserId()));
    }

}
