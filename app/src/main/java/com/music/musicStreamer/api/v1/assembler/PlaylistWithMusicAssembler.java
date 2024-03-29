package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.model.output.MusicOutput;
import com.music.musicStreamer.api.v1.model.output.PlaylistWithMusicOutput;
import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.playlist.PlaylistWithMusicEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaylistWithMusicAssembler implements Assembler<PlaylistWithMusicEntity, PlaylistWithMusicOutput>{
    private final Assembler<MusicEntity, MusicOutput> musicAssembler;

    @Override
    public PlaylistWithMusicOutput toOutput(PlaylistWithMusicEntity entity) {
        final var musicOutput = musicAssembler.toOutputList(entity.musicEntities());

        return PlaylistWithMusicOutput.builder()
                .id((long) entity.id())
                .name(entity.name())
                .music(musicOutput)
                .build();
    }
}
