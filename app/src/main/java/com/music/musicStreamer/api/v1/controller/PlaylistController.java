package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.model.dtos.AddMusicPlaylistDTO;
import com.music.musicStreamer.api.v1.model.dtos.CreatePlaylistDTO;
import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.usecases.playlist.AddMusicPlaylistUseCase;
import com.music.musicStreamer.usecases.playlist.CreatePlaylistUseCase;
import com.music.musicStreamer.usecases.playlist.GetPlaylistByIdUseCase;
import com.music.musicStreamer.usecases.playlist.GetPlaylistByUserIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music-streamer/v1/playlists")
public class PlaylistController {
    private final CreatePlaylistUseCase createPlaylistUseCase;
    private final GetPlaylistByIdUseCase getPlaylistByIdUseCase;
    private final AddMusicPlaylistUseCase addMusicPlaylistUseCase;
    private final GetPlaylistByUserIdUseCase getPlaylistByUserIdUseCase;


    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody CreatePlaylistDTO createPlaylist) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(createPlaylistUseCase.execute(createPlaylist.toEntity()));
    }

    @PostMapping("/addSong")
    public ResponseEntity<String> addSongToPlaylist(@RequestBody AddMusicPlaylistDTO addMusicPlaylist) {
        return ResponseEntity.status(HttpStatus.OK).body(addMusicPlaylistUseCase.execute(addMusicPlaylist.toEntity()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPlaylistById(@PathVariable("id") int playlistId) {
        return ResponseEntity.status(HttpStatus.OK).body(getPlaylistByIdUseCase.execute(playlistId));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getAllPlaylistsByUserId(@PathVariable("id") int userId) {
        Object playlists = getPlaylistByUserIdUseCase.execute(userId);
        return ResponseEntity.status(HttpStatus.OK).body(playlists);
    }

}
