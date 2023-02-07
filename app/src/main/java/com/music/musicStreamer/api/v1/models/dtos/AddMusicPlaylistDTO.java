package com.music.musicStreamer.api.v1.models.dtos;

import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import lombok.Data;

@Data
public class AddMusicPlaylistDTO {
    private int playlistId;
    private int musicId;
    private int userId;

    public MusicPlaylistRequest toRequest() {
        return new MusicPlaylistRequest(playlistId, musicId, userId);
    }
}
