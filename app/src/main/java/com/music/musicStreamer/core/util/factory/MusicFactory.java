package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.database.model.MusicModel;
import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.music.MusicRequest;
import com.music.musicStreamer.gateway.ImageGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MusicFactory {
    private final ImageGateway imageGateway;
    private @Value("${storage.music.mediaType}") String MUSIC_TYPE;

    public MusicEntity toEntity(final MusicModel musicModel) {
        final var image = imageGateway.getByMusicId(musicModel.getId());
        return new MusicEntity(
                musicModel.getId(),
                musicModel.getName(),
                musicModel.getArtist(),
                musicModel.getAlbum(),
                musicModel.getGenre(),
                image,
                musicModel.getPathName()
        );

    }

    public List<MusicEntity> toEntityList(final List<MusicModel> musicModel) {
        var musicEntityDTO = new ArrayList<MusicEntity>();

        for (final var music : musicModel) {
            final var image = imageGateway.getByMusicId(music.getId());

            var musicEntity2 = new MusicEntity(
                    music.getId(),
                    music.getName(),
                    music.getArtist(),
                    music.getAlbum(),
                    music.getGenre(),
                    image,
                    music.getPathName()
            );
            musicEntityDTO.add(musicEntity2);
        }

        return musicEntityDTO;
    }

    public MusicModel toModel(MusicRequest musicRequest, final String newFileName) {
        var musicModel = new MusicModel();
        musicModel.setName(musicRequest.getName());
        musicModel.setArtist(musicRequest.getArtist());
        musicModel.setAlbum(musicRequest.getAlbum());
        musicModel.setGenre(musicRequest.getGenre());
        musicModel.setPathName(newFileName + MUSIC_TYPE);
        musicModel.setCreated_at(new Date());
        musicModel.setUpdated_at(new Date());
        return musicModel;
    }
}
