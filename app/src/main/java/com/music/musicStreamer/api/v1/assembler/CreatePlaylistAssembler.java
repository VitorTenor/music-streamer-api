package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.response.CreatePlaylistResponse;
import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import org.springframework.stereotype.Component;

@Component
public class CreatePlaylistAssembler implements Assembler<PlaylistEntity, CreatePlaylistResponse> {

    @Override
    public CreatePlaylistResponse toOutput(PlaylistEntity entity) {
        return CreatePlaylistResponse.builder()
                .id((long) entity.getId())
                .name(entity.getName())
                .build();
    }
}
