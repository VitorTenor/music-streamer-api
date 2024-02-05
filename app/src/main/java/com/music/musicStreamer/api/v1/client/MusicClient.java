package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.database.model.MusicModel;
import com.music.musicStreamer.api.v1.database.repository.MusicRepository;
import com.music.musicStreamer.core.storage.impl.MusicFile;
import com.music.musicStreamer.core.util.MainUtils;
import com.music.musicStreamer.core.util.factory.MusicFactory;
import com.music.musicStreamer.entity.music.MusicDownload;
import com.music.musicStreamer.entity.music.MusicEntity;
import com.music.musicStreamer.entity.music.MusicRequest;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.exception.MusicException;
import com.music.musicStreamer.gateway.ImageGateway;
import com.music.musicStreamer.gateway.MusicGateway;
import com.music.musicStreamer.gateway.PlaylistMusicGateway;
import com.music.musicStreamer.usecase.image.DeleteImageByMusicIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class MusicClient implements MusicGateway {
    /*
     * Clients
     */
    private final ImageGateway imageGateway;
    private final PlaylistMusicGateway playlistMusicGateway;
    private final MusicFile musicFiles;
    private final MusicFactory musicFactory;
    private final MusicRepository musicRepository;

    private final DeleteImageByMusicIdUseCase deleteImageByMusicIdUseCase;
    private final Logger LOGGER = Logger.getLogger(MusicClient.class.getName());

    @Override
    @Transactional
    public MusicEntity saveMusic(MusicRequest musicRequest) {
        LOGGER.info("[MusicClient] Save music");

        String newFileName = MainUtils.randomName();

        LOGGER.info("[MusicClient] New file name => " + newFileName);

        musicFiles.saveInFiles(musicRequest, newFileName);

        LOGGER.info("[MusicClient] Music saved in files");

        MusicModel musicModel = saveInDatabase(musicFactory.createModel(musicRequest, newFileName));

        LOGGER.info("[MusicClient] Music saved in database");
        LOGGER.info("[MusicClient] musicId => " + musicModel.getId());

        return musicFactory.createMusic(musicModel);
    }

    @Override
    public List<MusicEntity> getAllMusics() {
        LOGGER.info("[MusicClient] Get all musics");

        return musicFiles.getAllInFiles();
    }

    @Override
    public byte[] playMusic(int musicId) throws IOException {
        LOGGER.info("[MusicClient] Play music by id");

        MusicModel musicModel = findMusicById(musicId);

        LOGGER.info("[MusicClient] Music found");
        LOGGER.info("[MusicClient] Music id: " + musicModel.getId());

        return musicFiles.getInFilesStream(musicModel.getPathName()).readAllBytes();
    }

    @Override
    public MusicDownload downloadMusic(int musicId) {
        LOGGER.info("[MusicClient] Download music by id");

        MusicModel musicModel = findMusicById(musicId);

        LOGGER.info("[MusicClient] Music found");
        LOGGER.info("[MusicClient] Music id: " + musicModel.getId());

        InputStream resource = musicFiles.getInFilesStream(musicModel.getPathName());

        File file = musicFiles.getFile(musicModel.getPathName());

        LOGGER.info("[MusicClient] Music downloaded");

        return new MusicDownload(resource, file);
    }

    @Override
    public MusicEntity getMusicById(int musicId) {
        LOGGER.info("[MusicClient] Get music by id");
        LOGGER.info("[MusicClient] Music id: " + musicId);

        MusicModel musicModel = findMusicById(musicId);

        return musicFactory.createMusic(musicModel);
    }

    @Override
    @Transactional
    public String deleteMusicById(int musicId) {
        LOGGER.info("[MusicClient] Delete music by id");

        MusicModel musicModel = findMusicById(musicId);

        LOGGER.info("[MusicClient] Music found");
        LOGGER.info("[MusicClient] Music name: " + musicModel.getName());

        musicRepository.deleteById(musicId);
        LOGGER.info("[MusicClient] Music deleted in database");

        if (Boolean.TRUE.equals(imageGateway.deleteByMusicId(musicId))) {
            LOGGER.info("[MusicClient] Image deleted in database");
        }

        if (Boolean.TRUE.equals(playlistMusicGateway.delete(musicId))) {
            LOGGER.info("[MusicClient] Music deleted in playlist");
        }

        musicFiles.deleteInFiles(musicModel.getPathName());
        LOGGER.info("[MusicClient] Music deleted in files");

        return MusicMessages.MUSIC_DELETED.getMessage();
    }

    private MusicModel saveInDatabase(MusicModel music) {
        LOGGER.info("[MusicClient] Save music in database");
        return musicRepository.save(music);
    }

    private MusicModel findMusicById(int id) {
        LOGGER.info("[MusicClient] Find music by id");
        return musicRepository.findById(id).orElseThrow(() -> new MusicException(MusicMessages.NOT_FOUND));
    }
}
