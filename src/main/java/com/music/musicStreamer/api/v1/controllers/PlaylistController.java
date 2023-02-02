package com.music.musicStreamer.api.v1.controllers;

import com.music.musicStreamer.api.v1.models.MusicPlaylistRegisterDTO;
import com.music.musicStreamer.api.v1.models.PlaylistRegisterDTO;
import com.music.musicStreamer.domain.repositories.PlaylistRepository;
import com.music.musicStreamer.domain.repositories.UserRepository;
import com.music.musicStreamer.domain.services.PlaylistService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;

    public PlaylistController(PlaylistService playlistService, UserRepository userRepository, PlaylistRepository playlistRepository) {
        this.playlistService = playlistService;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
    }

    @ApiOperation(value = "Create playlist")
    @PostMapping
    public ResponseEntity<Object> createPlaylist(@RequestBody PlaylistRegisterDTO playlist) {
        if (userRepository.existsById(playlist.getUserId())) {
            return new ResponseEntity<>(playlistService.createPlaylist(playlist), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Add music to playlist")
    @PostMapping("/addSong")
    public ResponseEntity<Object> addSongToPlaylist(@RequestBody MusicPlaylistRegisterDTO musicPlaylistRegisterDTO) {
        if (playlistRepository.existsById(musicPlaylistRegisterDTO.getPlaylistId())) {
            return ResponseEntity.status(HttpStatus.OK).body(playlistService.addSongToPlaylist(musicPlaylistRegisterDTO));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @ApiOperation(value = "Get playlist")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPlaylistById(@PathVariable("id") int playlistId) {
        if(playlistRepository.existsById(playlistId)) {
            return ResponseEntity.status(HttpStatus.OK).body(playlistService.getPlaylistById(playlistId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
    }

    @ApiOperation(value = "Get all playlists from user")
    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getAllPlaylistsByUserId(@PathVariable("id") int userId) {
        if (userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.OK).body(playlistService.getPlaylistByUserId(userId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
