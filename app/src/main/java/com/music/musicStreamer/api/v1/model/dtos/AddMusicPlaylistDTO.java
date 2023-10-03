package com.music.musicStreamer.api.v1.model.dtos;

import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import lombok.Data;

@Data
public class AddMusicPlaylistDTO {
    private int playlistId;
    private int musicId;
    private int userId;

    public MusicPlaylistRequest toEntity() {
        return new MusicPlaylistRequest(playlistId, musicId, userId);
    }
}
