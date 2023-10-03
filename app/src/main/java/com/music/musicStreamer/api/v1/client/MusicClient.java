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

    @Override
    @Transactional
    public Music saveMusic(MusicRequest musicRequest) {
        musicValidator.validateMusic(musicRequest);

        String newFileName = generateName.randomName();

        musicFiles.saveInFiles(musicRequest, newFileName);

        MusicModel musicModel = saveInDatabase(musicFactory.createModel(musicRequest, newFileName));

        return musicFactory.createMusic(musicModel);
    }

    @Override
    public List<Music> getAllMusics() {
        return musicFiles.getAllInFiles();
    }

    @Override
    public Object playMusic(int id) {
        MusicModel musicModel = findAndValidateByMusicId(id);
        return musicFiles.getInFilesStream(musicModel.getPathName());
    }

    @Override
    public MusicDownload downloadMusic(int id) {
        MusicModel musicModel = findAndValidateByMusicId(id);

        InputStream resource = musicFiles.getInFilesStream(musicModel.getPathName());
        File file = musicFiles.getFile(musicModel.getPathName());

        return new MusicDownload(resource, file);
    }

    @Override
    public Music getMusicById(int id) {
        MusicModel musicModel = findAndValidateByMusicId(id);

        return musicFactory.createMusic(musicModel);
    }

    @Override
    @Transactional
    public String deleteMusicById(int id) {
        MusicModel musicModel = findAndValidateByMusicId(id);

        musicRepository.deleteById(id);
        deleteImageByMusicIdUseCase.execute(id);
        deleteMusicFromPlaylistUseCase.execute(id);
        musicFiles.deleteInFiles(musicModel.getPathName());

        return MusicMessages.MUSIC_DELETED.getMessage();
    }

    private MusicModel findAndValidateByMusicId(int id) {
        MusicModel musicModel = findMusicById(id);
        musicValidator.validateIfMusicIsNotNull(musicModel);
        return musicModel;
    }

    private MusicModel saveInDatabase(MusicModel music) {
        return musicRepository.save(music);
    }

    private MusicModel findMusicById(int id) {
        return musicRepository.findById(id).orElseThrow(() -> new MusicException(MusicMessages.NOT_FOUND));
    }
}
