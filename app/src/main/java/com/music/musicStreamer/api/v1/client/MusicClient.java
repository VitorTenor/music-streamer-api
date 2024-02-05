package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.database.model.MusicModel;
import com.music.musicStreamer.api.v1.database.repository.MusicRepository;
import com.music.musicStreamer.core.storage.FileBase;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
@RequiredArgsConstructor
public class MusicClient implements MusicGateway {
    /*
     * Clients
     */
    private final ImageGateway imageGateway;
    private final PlaylistMusicGateway playlistMusicGateway;
    private final MusicFactory musicFactory;
    private final MusicRepository musicRepository;
    private final FileBase<MusicRequest> fileBase;

    @Override
    @Transactional
    public MusicEntity saveMusic(MusicRequest musicRequest) {
        info(this.getClass(), "Save music");

        final var newFileName = MainUtils.randomName();
        info(this.getClass(), "New file name => " + newFileName);

        fileBase.saveInFiles(musicRequest, newFileName);
        info(this.getClass(), "Music saved in files");

        final var musicModel = saveInDatabase(musicFactory.toModel(musicRequest, newFileName));
        info(this.getClass(), "Music saved in database");
        info(this.getClass(), "musicId => " + musicModel.getId());

        return musicFactory.toEntity(musicModel);
    }

    @Override
    public List<MusicEntity> getAllMusics() {
        info(this.getClass(), "Get all musics");

        return fileBase.getAllInFiles();
    }

    @Override
    public byte[] playMusic(int musicId) {
        info(this.getClass(), "Play music by id");

        MusicModel musicModel = findMusicById(musicId);

        info(this.getClass(), "Music found");
        info(this.getClass(), "Music id: " + musicModel.getId());

        try {
            return fileBase.getInFilesStream(musicModel.getPathName()).readAllBytes();
        } catch (Exception e) {
            throw new MusicException(MusicMessages.NOT_FOUND);
        }
    }

    @Override
    public MusicDownload downloadMusic(int musicId) {
        info(this.getClass(), "Download music by id");

        MusicModel musicModel = findMusicById(musicId);

        info(this.getClass(), "Music found");
        info(this.getClass(), "Music id: " + musicModel.getId());

        InputStream resource = fileBase.getInFilesStream(musicModel.getPathName());

        File file = fileBase.getFile(musicModel.getPathName());

        info(this.getClass(), "Music downloaded");

        return new MusicDownload(resource, file);
    }

    @Override
    public MusicEntity getMusicById(int musicId) {
        info(this.getClass(), "Get music by id");
        info(this.getClass(), "Music id: " + musicId);

        MusicModel musicModel = findMusicById(musicId);

        return musicFactory.toEntity(musicModel);
    }

    @Override
    @Transactional
    public String deleteMusicById(int musicId) {
        info(this.getClass(), "Delete music by id");

        var musicModel = findMusicById(musicId);

        info(this.getClass(), "Music found");
        info(this.getClass(), "Music id: " + musicModel.getId());

        musicRepository.deleteById(musicId);
        info(this.getClass(), "Music deleted in database");

        if (Boolean.TRUE.equals(imageGateway.delete(musicId))) {
            info(this.getClass(), "Image deleted in database");
        }

        if (Boolean.TRUE.equals(playlistMusicGateway.delete(musicId))) {
            info(this.getClass(), "Music deleted in playlist");
        }

        fileBase.deleteInFiles(musicModel.getPathName());
        info(this.getClass(), "Music deleted in files");

        return MusicMessages.MUSIC_DELETED.getMessage();
    }

    private MusicModel saveInDatabase(MusicModel music) {
        info(this.getClass(), "Save music in database");
        return musicRepository.save(music);
    }

    private MusicModel findMusicById(int id) {
        info(this.getClass(), "Find music by id");
        return musicRepository.findById(id).orElseThrow(() -> new MusicException(MusicMessages.NOT_FOUND));
    }
}
