package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.model.ImageModel;
import com.music.musicStreamer.api.v1.repository.ImageRepository;
import com.music.musicStreamer.core.util.GenerateName;
import com.music.musicStreamer.core.storage.impl.ImageFiles;
import com.music.musicStreamer.core.util.factory.ImageFactory;
import com.music.musicStreamer.core.util.validator.ImageValidator;
import com.music.musicStreamer.entity.image.Image;
import com.music.musicStreamer.entity.image.ImageRequest;
import com.music.musicStreamer.gateway.ImageGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class ImageClient implements ImageGateway {
    private final ImageFiles imageFiles;
    private final ImageFactory imageFactory;
    private final ImageValidator imageValidator;
    private final ImageRepository imageRepository;
    private final Logger LOGGER = Logger.getLogger(ImageClient.class.getName());

    @Override
    @Transactional
    public Image saveImage(ImageRequest imageRequest) {
        LOGGER.info("[ImageClient] Upload image");

        String newFileName = GenerateName.randomName();

        LOGGER.info("[ImageClient] New file name => " + newFileName);

        imageFiles.saveInFiles(imageRequest, newFileName);

        LOGGER.info("[ImageClient] Image saved in files");

        ImageModel imageModel = saveInDatabase(imageFactory.createImageModel(imageRequest, newFileName));

        LOGGER.info("[ImageClient] Image saved in database => imageId: " + imageModel.getId());

        return imageFactory.createImage(imageModel);
    }

    @Override
    @Transactional
    public Boolean deleteImageByMusicId(int id) {
        LOGGER.info("[ImageClient] Delete image by music id");

        ImageModel imageModel = findByMusicId(id);

        if (imageModel == null) {
            LOGGER.info("[ImageClient] Image not found, nothing to delete");
            return false;
        }

        imageFiles.deleteInFiles(imageModel.getPathName());

        LOGGER.info("[ImageClient] Image deleted in files");

        deleteInDatabaseByImageId(imageModel.getId());
        LOGGER.info("[ImageClient] Image deleted in database");

        return true;
    }

    @Override
    public byte[] getImageByFileName(String fileName) {
        LOGGER.info("[ImageClient] Get image by file name");
        LOGGER.info("[ImageClient] File name => " + fileName);

        return imageFiles.getBytesInFiles(fileName);
    }

    @Override
    public Image getImageById(int id) {
        ImageModel imageModel = findAndValidateByImageId(id);
        return imageFactory.createImage(imageModel);
    }

    @Override
    public Image getImageByMusicId(int id) {
        LOGGER.info("[ImageClient] Get image by music id");
        ImageModel imageModel = findByMusicId(id);


        return imageModel != null ?
                imageFactory.createImage(imageModel) : imageFactory.createDefaultImage();
    }

    private void deleteInDatabaseByImageId(int id) {
        LOGGER.info("[ImageClient] Delete image by id");
        imageRepository.deleteById(id);
    }

    private ImageModel saveInDatabase(ImageModel imageModel) {
        return imageRepository.save(imageModel);
    }

    private ImageModel findByMusicId(int musicId) {
        LOGGER.info("[ImageClient] Find image by music id");
        LOGGER.info("[ImageClient] Music id => " + musicId);

        return imageRepository.findByMusicId(musicId);
    }

    private ImageModel findAndValidateByImageId(int id) {
        LOGGER.info("[ImageClient] Find image by id");

        ImageModel imageModel = imageRepository.findByMusicId(id);
        imageValidator.validateIfImageIsNotNull(imageModel);

        LOGGER.info("[ImageClient] Image found");
        return imageModel;
    }
}
