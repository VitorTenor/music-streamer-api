package com.music.musicStreamer.controllers;

import com.music.musicStreamer.models.PlaylistModel;
import com.music.musicStreamer.models.PlaylistMusicModel;
import com.music.musicStreamer.repositories.PlaylistRepository;
import com.music.musicStreamer.repositories.UserRepository;
import com.music.musicStreamer.services.PlaylistService;
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

    @PostMapping
    public ResponseEntity createPlaylist(@RequestBody PlaylistModel playlist) {
        if (userRepository.existsById(playlist.getUser_id())) {
            return new ResponseEntity<>(playlistService.createPlaylist(playlist), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addSong")
    public ResponseEntity addSongToPlaylist(@RequestBody PlaylistMusicModel playlistMusicModel) {
        if (playlistRepository.existsById(playlistMusicModel.getPlaylist_id())) {
            return ResponseEntity.status(HttpStatus.OK).body(playlistService.addSongToPlaylist(playlistMusicModel));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity getPlaylistById(@PathVariable("id") int playlistId) {
        if(playlistRepository.existsById(playlistId)) {
            return ResponseEntity.status(HttpStatus.OK).body(playlistService.getPlaylistById(playlistId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
    }

    @GetMapping("/all/{id}")
    public ResponseEntity getAllPlaylistsByUserId(@PathVariable("id") int userId) {
        if (userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.OK).body(playlistService.getPlaylistByUserId(userId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
