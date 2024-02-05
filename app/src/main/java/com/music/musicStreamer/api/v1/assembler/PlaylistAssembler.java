package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.model.output.PlaylistOutput;
import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import org.springframework.stereotype.Component;

@Component
public class PlaylistAssembler implements Assembler<PlaylistEntity, PlaylistOutput> {

    @Override
    public PlaylistOutput toOutput(PlaylistEntity entity) {
        return PlaylistOutput.builder()
                .id(entity.id())
                .name(entity.name())
                .musicIds(entity.musicIds())
                .build();
    }
}
