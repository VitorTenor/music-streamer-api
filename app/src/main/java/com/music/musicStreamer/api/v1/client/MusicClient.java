package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.model.MusicModel;
import com.music.musicStreamer.api.v1.repository.MusicRepository;
import com.music.musicStreamer.core.util.GenerateName;
import com.music.musicStreamer.core.storage.impl.MusicFiles;
import com.music.musicStreamer.core.util.factory.MusicFactory;
import com.music.musicStreamer.core.util.validator.MusicValidator;
import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.entity.music.MusicDownload;
import com.music.musicStreamer.entity.music.MusicRequest;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.exception.MusicException;
import com.music.musicStreamer.gateway.MusicGateway;
import com.music.musicStreamer.usecase.image.DeleteImageByMusicIdUseCase;

import com.music.musicStreamer.usecase.playlistMusic.DeleteMusicFromPlaylistUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class MusicClient implements MusicGateway {
    private final MusicFiles musicFiles;
    private final GenerateName generateName;
    private final MusicFactory musicFactory;
    private final MusicValidator musicValidator;
    private final MusicRepository musicRepository;
    private final DeleteImageByMusicIdUseCase deleteImageByMusicIdUseCase;
    private final DeleteMusicFromPlaylistUseCase deleteMusicFromPlaylistUseCase;
    private final Logger LOGGER = Logger.getLogger(MusicClient.class.getName());

    @Override
    @Transactional
    public Music saveMusic(MusicRequest musicRequest) {
        LOGGER.info("[MusicClient] Save music");

        musicValidator.validateMusic(musicRequest);

        LOGGER.info("[MusicClient] Music is valid");

        String newFileName = generateName.randomName();

        LOGGER.info("[MusicClient] New file name => " + newFileName);

        musicFiles.saveInFiles(musicRequest, newFileName);

        LOGGER.info("[MusicClient] Music saved in files");

        MusicModel musicModel = saveInDatabase(musicFactory.createModel(musicRequest, newFileName));

        LOGGER.info("[MusicClient] Music saved in database");
        LOGGER.info("[MusicClient] musicId => " + musicModel.getId());

        return musicFactory.createMusic(musicModel);
    }

    @Override
    public List<Music> getAllMusics() {
        LOGGER.info("[MusicClient] Get all musics");

        return musicFiles.getAllInFiles();
    }

    @Override
    public Object playMusic(int musicId) {
        LOGGER.info("[MusicClient] Play music by id");

        MusicModel musicModel = findAndValidateByMusicId(musicId);

        LOGGER.info("[MusicClient] Music found");
        LOGGER.info("[MusicClient] Music id: " + musicModel.getId());

        return musicFiles.getInFilesStream(musicModel.getPathName());
    }

    @Override
    public MusicDownload downloadMusic(int musicId) {
        LOGGER.info("[MusicClient] Download music by id");

        MusicModel musicModel = findAndValidateByMusicId(musicId);

        LOGGER.info("[MusicClient] Music found");
        LOGGER.info("[MusicClient] Music id: " + musicModel.getId());

        InputStream resource = musicFiles.getInFilesStream(musicModel.getPathName());

        File file = musicFiles.getFile(musicModel.getPathName());

        LOGGER.info("[MusicClient] Music downloaded");

        return new MusicDownload(resource, file);
    }

    @Override
    public Music getMusicById(int musicId) {
        LOGGER.info("[MusicClient] Get music by id");
        LOGGER.info("[MusicClient] Music id: " + musicId);

        MusicModel musicModel = findAndValidateByMusicId(musicId);

        return musicFactory.createMusic(musicModel);
    }

    @Override
    @Transactional
    public String deleteMusicById(int musicId) {
        LOGGER.info("[MusicClient] Delete music by id");

        MusicModel musicModel = findAndValidateByMusicId(musicId);

        LOGGER.info("[MusicClient] Music found");
        LOGGER.info("[MusicClient] Music name: " + musicModel.getName());

        musicRepository.deleteById(musicId);
        LOGGER.info("[MusicClient] Music deleted in database");

        deleteImageByMusicIdUseCase.execute(musicId);
        LOGGER.info("[MusicClient] Image deleted in database");

        deleteMusicFromPlaylistUseCase.execute(musicId);
        LOGGER.info("[MusicClient] Music deleted in playlist");

        musicFiles.deleteInFiles(musicModel.getPathName());
        LOGGER.info("[MusicClient] Music deleted in files");

        return MusicMessages.MUSIC_DELETED.getMessage();
    }

    private MusicModel findAndValidateByMusicId(int musicId) {
        LOGGER.info("[MusicClient] Find music by id");

        MusicModel musicModel = findMusicById(musicId);

        LOGGER.info("[MusicClient] Music found");

        musicValidator.validateIfMusicIsNotNull(musicModel);

        LOGGER.info("[MusicClient] Music is valid");
        LOGGER.info("[MusicClient] Music id: " + musicModel.getId());

        return musicModel;
    }

    private MusicModel saveInDatabase(MusicModel music) {
        LOGGER.info("[MusicClient] Save music in database");
        return musicRepository.save(music);
    }

    private MusicModel findMusicById(int id) {
        return musicRepository.findById(id).orElseThrow(() -> new MusicException(MusicMessages.NOT_FOUND));
    }
}
