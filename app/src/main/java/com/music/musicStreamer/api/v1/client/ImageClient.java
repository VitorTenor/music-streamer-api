package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.database.model.ImageModel;
import com.music.musicStreamer.api.v1.database.repository.ImageRepository;
import com.music.musicStreamer.api.v1.database.repository.MusicRepository;
import com.music.musicStreamer.core.storage.FileBase;
import com.music.musicStreamer.core.util.MainUtils;
import com.music.musicStreamer.core.util.factory.ImageFactory;
import com.music.musicStreamer.entity.image.ImageEntity;
import com.music.musicStreamer.entity.image.UploadImageEntity;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.exception.MusicException;
import com.music.musicStreamer.gateway.ImageGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
@RequiredArgsConstructor
public class ImageClient implements ImageGateway {
    /*
     * Factory
     */
    private final ImageFactory imageFactory;

    private final FileBase<UploadImageEntity> fileBase;
    private final ImageRepository imageRepository;
    private final MusicRepository musicRepository;

    @Override
    @Transactional
    public ImageEntity save(UploadImageEntity entity) {
        info(this.getClass(), "Upload image");

        final var musicModel = musicRepository.findById(entity.musicId())
                .orElseThrow(() -> new MusicException(MusicMessages.NOT_FOUND));

        info(this.getClass(), "Music found");
        info(this.getClass(), "Music name => " + musicModel.getName());

        final var newFileName = MainUtils.randomName();
        info(this.getClass(), "New file name => " + newFileName);

        fileBase.saveInFiles(entity, newFileName);
        info(this.getClass(), "Image saved in files");

        final var imageModel = saveInDatabase(imageFactory.toModel(musicModel, newFileName));
        info(this.getClass(), "Image saved in database => imageId: " + imageModel.getId());

        return imageFactory.toEntity(imageModel);
    }

    @Override
    @Transactional
    public Boolean delete(final int id) {
        info(this.getClass(), "Delete image by music id");

        final var imageModel = findByMusicId(id);
        if (Objects.isNull(imageModel)) {
            info(this.getClass(), "Image not found, nothing to delete");
            return Boolean.FALSE;
        }

        fileBase.deleteInFiles(imageModel.getPathName());
        info(this.getClass(), "Image deleted in files");

        deleteInDatabaseByImageId(imageModel.getId());
        info(this.getClass(), "Image deleted in database");

        return Boolean.TRUE;
    }

    @Override
    public byte[] getByFileName(final String fileName) {
        info(this.getClass(), "Get image by file name");
        info(this.getClass(), "File name => " + fileName);

        return fileBase.getBytesInFiles(fileName);
    }

    @Override
    public ImageEntity getByMusicId(int id) {
        info(this.getClass(), "Get image by music id");
        final var imageModel = findByMusicId(id);

        if (Objects.isNull(imageModel)) {
            info(this.getClass(), "Image not found");
            info(this.getClass(), "Return default image");
            return imageFactory.toEntityDefault();
        } else {
            info(this.getClass(), "Image found");
            return imageFactory.toEntity(imageModel);
        }
    }

    private void deleteInDatabaseByImageId(final int id) {
        info(this.getClass(), "Delete image by id");
        imageRepository.deleteById(id);
    }

    private ImageModel saveInDatabase(final ImageModel imageModel) {
        info(this.getClass(), "Save image in database");
        return imageRepository.save(imageModel);
    }

    private ImageModel findByMusicId(final int musicId) {
        info(this.getClass(), "Find image by music id");
        info(this.getClass(), "Music id => " + musicId);

        return imageRepository.findByMusicId(musicId);
    }
}
