package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.PlaylistMusicModel;

import com.music.musicStreamer.api.v1.repositories.MusicRepository;
import com.music.musicStreamer.api.v1.repositories.PlaylistMusicRepository;
import com.music.musicStreamer.api.v1.repositories.PlaylistRepository;
import com.music.musicStreamer.domain.models.PlaylistModel;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.entities.playlist.PlaylistMusic;
import com.music.musicStreamer.entities.playlist.PlaylistRequest;
import com.music.musicStreamer.gateways.PlaylistGateway;
import com.music.musicStreamer.usecases.playlistMusic.GetMusicByPlaylistIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaylistClient implements PlaylistGateway {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final GetMusicByPlaylistIdUseCase getMusicByPlaylistIdUseCase;

    @Override
    public Playlist createPlaylist(PlaylistRequest playlistRequest) throws Exception {
        PlaylistModel playlistCreated = new PlaylistModel(playlistRequest.getName(), playlistRequest.getUserId(), new Date(), new Date());
        try {
            playlistRepository.save(playlistCreated);
        } catch (Exception e) {
            throw new Exception("Error creating playlist");
        }
        return new Playlist(playlistCreated.getName(), playlistCreated.getUserId(), playlistCreated.getId());
    }

    @Override
    public String addMusicToPlaylist(MusicPlaylistRequest musicPlaylistRequest) {
        try {
            Boolean playlistExists = playlistRepository.existsById(musicPlaylistRequest.getPlaylistId());
            Boolean musicExists = musicRepository.existsById(musicPlaylistRequest.getMusicId());
            if (playlistExists && musicExists) {
                PlaylistMusicModel playlistMusicModel = new PlaylistMusicModel(musicPlaylistRequest.getPlaylistId(), musicPlaylistRequest.getUserId(), musicPlaylistRequest.getMusicId(), new Date(), new Date());
                playlistMusicRepository.save(playlistMusicModel);
                return "Music added to playlist";
            }
        } catch (Exception e) {
            return "Error adding music to playlist";
        }
        return "Error adding music to playlist";
    }

    @Override
    public PlaylistMusic getPlaylistById(int id) {
        PlaylistModel playlistModel = playlistRepository.findById(id).orElseThrow(() -> new RuntimeException("Playlist not found"));
        return new PlaylistMusic(playlistModel.getId(), playlistModel.getName(), playlistModel.getUserId(), getMusicByList(id));
    }

    @Override
    public List<Playlist> getPlaylistByUserId(int id) {
        try {
            List<PlaylistModel> playlistModel = playlistRepository.findAllByUserId(id);
            List<Playlist> playlists = new ArrayList<>();
            for (PlaylistModel playlist : playlistModel) {
                Playlist playlistMusic = new Playlist(playlist.getName(), playlist.getUserId(), playlist.getId());
                playlists.add(playlistMusic);
            }
            return playlists;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting playlist by user id");
        }
    }

    private List<Music> getMusicByList(int playlistId) {
        List<Music> music1 = new ArrayList<>();
        for (Music music : getMusicByPlaylistIdUseCase.execute(playlistId)) {
            Music music2 = new Music(music.getId(), music.getName(), music.getArtist(), music.getAlbum(), music.getGenre(), music.getImage(), music.getPath_name());
            music1.add(music2);
        }
        return music1;
    }
}

