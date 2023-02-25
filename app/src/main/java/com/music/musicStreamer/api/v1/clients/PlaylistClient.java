package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.PlaylistModel;
import com.music.musicStreamer.api.v1.models.PlaylistMusicModel;

import com.music.musicStreamer.api.v1.repositories.MusicRepository;
import com.music.musicStreamer.api.v1.repositories.PlaylistMusicRepository;
import com.music.musicStreamer.api.v1.repositories.PlaylistRepository;
import com.music.musicStreamer.api.v1.repositories.UserRepository;
import com.music.musicStreamer.entities.music.Music;
import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.entities.playlist.PlaylistMusic;
import com.music.musicStreamer.entities.playlist.PlaylistRequest;
import com.music.musicStreamer.exceptions.MusicException;
import com.music.musicStreamer.exceptions.PlaylistException;
import com.music.musicStreamer.exceptions.UserException;
import com.music.musicStreamer.gateways.PlaylistGateway;
import com.music.musicStreamer.usecases.playlistMusic.GetMusicByPlaylistIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaylistClient implements PlaylistGateway {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;
    private final UserRepository userRepository;
    private final MusicRepository musicRepository;
    private final GetMusicByPlaylistIdUseCase getMusicByPlaylistIdUseCase;

    @Override
    @Transactional
    public Playlist createPlaylist(PlaylistRequest playlistRequest) {
        validatePlaylist(playlistRequest);
        PlaylistModel playlistCreated = new PlaylistModel(playlistRequest.getName(), playlistRequest.getUserId(), new Date(), new Date());
        try {
            playlistRepository.save(playlistCreated);
            return new Playlist(playlistCreated.getName(), playlistCreated.getUserId(), playlistCreated.getId());
        } catch (Exception e) {
            throw new PlaylistException("Error creating playlist");
        }
    }

    @Override
    @Transactional
    public String addMusicToPlaylist(MusicPlaylistRequest musicPlaylistRequest) {
        if (!playlistRepository.existsById(musicPlaylistRequest.getPlaylistId())) throw new PlaylistException("Playlist not found");
        if (!musicRepository.existsById(musicPlaylistRequest.getMusicId())) throw new MusicException("Music not found");
        if (!userRepository.existsById(musicPlaylistRequest.getUserId())) throw new UserException("User not found");
        try {
            PlaylistMusicModel playlistMusicModel = new PlaylistMusicModel(musicPlaylistRequest.getPlaylistId(), musicPlaylistRequest.getUserId(), musicPlaylistRequest.getMusicId(), new Date(), new Date());
            playlistMusicRepository.save(playlistMusicModel);
            return "Music added to playlist";
        } catch (Exception e) {
            throw new PlaylistException("Error adding music to playlist");
        }
    }

    @Override
    public PlaylistMusic getPlaylistById(int id) {
        PlaylistModel playlistModel = playlistRepository.findById(id).orElseThrow(() -> new PlaylistException("Playlist not found"));
        return new PlaylistMusic(playlistModel.getId(), playlistModel.getName(), playlistModel.getUserId(), getMusicByList(id));
    }

    @Override
    public List<Playlist> getPlaylistByUserId(int id) {
        if (!userRepository.existsById(id)) throw new UserException("User not found");
        try {
            return playlistRepository.findAllByUserId(id).stream().map(playlist -> new Playlist(playlist.getName(), playlist.getUserId(), playlist.getId())).toList();
        } catch (Exception e) {
            throw new PlaylistException("Error while getting playlist by user id");
        }
    }

    private List<Music> getMusicByList(int playlistId) {
        try {
            List<Music> music1 = new ArrayList<>();
            for (Music music : getMusicByPlaylistIdUseCase.execute(playlistId)) {
                Music music2 = new Music(music.getId(), music.getName(), music.getArtist(), music.getAlbum(), music.getGenre(), music.getImage(), music.getPath_name());
                music1.add(music2);
            }
            return music1;
        }catch (Exception e){
            throw new PlaylistException("Error while getting music by playlist id");
        }
    }

    private void validatePlaylist(PlaylistRequest playlistRequest) {
        if (playlistRequest.getName().isEmpty()) throw new PlaylistException("Playlist name is required");
        if (playlistRequest.validateUserId()) throw new PlaylistException("User id is required");
    }
}

