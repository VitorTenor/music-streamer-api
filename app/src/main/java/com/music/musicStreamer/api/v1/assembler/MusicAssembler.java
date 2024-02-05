package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.model.output.MusicOutput;
import com.music.musicStreamer.entity.music.Music;
import org.springframework.stereotype.Component;

@Component
public class MusicAssembler implements Assembler<Music, MusicOutput>{
    @Override
    public MusicOutput toOutput(Music entity) {
        return MusicOutput.builder()
                .id(entity.getId())
                .name(entity.getName())
                .artist(entity.getArtist())
                .album(entity.getAlbum())
                .genre(entity.getGenre())
                .pathName(entity.getPathName())
                .build();
    }
}
