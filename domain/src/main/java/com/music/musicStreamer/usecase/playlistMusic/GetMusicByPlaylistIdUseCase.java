package com.music.musicStreamer.usecase.playlistMusic;

import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.gateway.PlaylistMusicGateway;

import javax.inject.Named;
import java.util.List;

@Named
public class GetMusicByPlaylistIdUseCase {
    private final PlaylistMusicGateway playlistMusicGateway;

    public GetMusicByPlaylistIdUseCase(PlaylistMusicGateway playlistMusicGateway) {
        this.playlistMusicGateway = playlistMusicGateway;
    }

    public List<Music> execute(int id) {

        return playlistMusicGateway.getMusicByPlaylistId(id);
    }

}
