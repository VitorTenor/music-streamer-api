package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.model.MusicModel;
import com.music.musicStreamer.entity.music.Music;
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
    private static @Value("${storage.music.mediaType}") String MUSIC_TYPE;
    private final Logger LOGGER = Logger.getLogger(MusicFactory.class.getName());

    public Music createMusic(MusicModel musicModel) {
        LOGGER.info("[MusicFactory] Create music");

        return new Music(
                musicModel.getId(),
                musicModel.getName(),
                musicModel.getArtist(),
                musicModel.getAlbum(),
                musicModel.getGenre(),
                getImageByMusicIdUseCase.execute(musicModel.getId()),
                musicModel.getPathName()
        );

    }

    public List<Music> createMusicList(List<MusicModel> musicModel) {
        LOGGER.info("[MusicFactory] Create music list");
        List<Music> musicDTO = new ArrayList<>();

        for (MusicModel music : musicModel) {
            Music music2 = new Music(
                    music.getId(),
                    music.getName(),
                    music.getArtist(),
                    music.getAlbum(),
                    music.getGenre(),
                    getImageByMusicIdUseCase.execute(music.getId()),
                    music.getPathName()
            );
            musicDTO.add(music2);
        }

        LOGGER.info("[MusicFactory] Music list created");
        LOGGER.info("[MusicFactory] Music list size => " + musicDTO.size());

        return musicDTO;
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
