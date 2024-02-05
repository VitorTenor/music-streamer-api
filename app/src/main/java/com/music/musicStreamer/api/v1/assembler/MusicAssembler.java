package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.model.output.MusicOutput;
import com.music.musicStreamer.entity.music.MusicEntity;
import org.springframework.stereotype.Component;

@Component
public class MusicAssembler implements Assembler<MusicEntity, MusicOutput>{
    @Override
    public MusicOutput toOutput(MusicEntity entity) {
        return MusicOutput.builder()
                .id(entity.id())
                .name(entity.name())
                .artist(entity.artist())
                .album(entity.album())
                .genre(entity.genre())
                .pathName(entity.pathName())
                .build();
    }
}
