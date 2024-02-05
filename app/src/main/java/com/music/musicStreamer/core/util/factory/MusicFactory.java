package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.database.model.MusicModel;
import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.music.MusicRequest;
import com.music.musicStreamer.usecase.image.GetImageByMusicIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class MusicFactory {
    private final GetImageByMusicIdUseCase getImageByMusicIdUseCase;
    private @Value("${storage.music.mediaType}") String MUSIC_TYPE;
    private final Logger LOGGER = Logger.getLogger(MusicFactory.class.getName());

    public MusicEntity createMusic(MusicModel musicModel) {
        LOGGER.info("[MusicFactory] Create music");

        return new MusicEntity(
                musicModel.getId(),
                musicModel.getName(),
                musicModel.getArtist(),
                musicModel.getAlbum(),
                musicModel.getGenre(),
                getImageByMusicIdUseCase.execute(musicModel.getId()),
                musicModel.getPathName()
        );

    }

    public List<MusicEntity> createMusicList(List<MusicModel> musicModel) {
        LOGGER.info("[MusicFactory] Create music list");
        List<MusicEntity> musicEntityDTO = new ArrayList<>();

        for (MusicModel music : musicModel) {
            MusicEntity musicEntity2 = new MusicEntity(
                    music.getId(),
                    music.getName(),
                    music.getArtist(),
                    music.getAlbum(),
                    music.getGenre(),
                    getImageByMusicIdUseCase.execute(music.getId()),
                    music.getPathName()
            );
            musicEntityDTO.add(musicEntity2);
        }

        LOGGER.info("[MusicFactory] Music list created");
        LOGGER.info("[MusicFactory] Music list size => " + musicEntityDTO.size());

        return musicEntityDTO;
    }

    public MusicModel createModel(MusicRequest musicRequest, String newFileName) {
        MusicModel musicModel = new MusicModel();
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
