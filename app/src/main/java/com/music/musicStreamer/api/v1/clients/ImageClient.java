package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.ImageModel;
import com.music.musicStreamer.api.v1.repositories.ImageRepository;
import com.music.musicStreamer.core.GenerateName;
import com.music.musicStreamer.core.storage.impl.ImageFiles;
import com.music.musicStreamer.core.utils.factories.ImageFactory;
import com.music.musicStreamer.core.utils.validators.ImageValidator;
import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.entities.image.ImageRequest;
import com.music.musicStreamer.gateways.ImageGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class ImageClient implements ImageGateway {
        private static @Value("${storage.image.url}") String IMAGE_URL;

        private final GenerateName generateName;
        private final ImageRepository imageRepository;
        private final ImageValidator imageValidator;
        private final ImageFactory imageFactory;
        private final ImageFiles imageFiles;

        @Override
        @Transactional
        public Image saveImage(ImageRequest imageRequest) {
                imageValidator.validateImage(imageRequest);
                String newFileName = generateName.randomName();
                imageFiles.saveInFiles(imageRequest, newFileName);
                ImageModel imageModel = saveInDatabase(imageFactory.createImageModel(imageRequest, newFileName));
                return imageFactory.createImage(imageModel);
        }

        @Override
        @Transactional
        public Boolean deleteImageByMusicId(int id) {
                ImageModel imageModel = findAndValidateByMusicId(id);
                imageFiles.deleteInFiles(imageModel.getPathName());
                deleteInDatabaseByImageId(imageModel.getId());
                return true;
        }

        @Override
        public byte[] getImageByFileName(String fileName) {
                return imageFiles.getBytesInFiles(fileName);
        }

        @Override
        public Image getImageById(int id) {
                ImageModel imageModel = findAndValidateByImageId(id);
                return imageFactory.createImage(imageModel);
        }

        @Override
        public Image getImageByMusicId(int id) {
                ImageModel imageModel = findAndValidateByMusicId(id);
                return imageFactory.createImage(imageModel);
        }

        private void deleteInDatabaseByImageId(int id) {
                imageRepository.deleteById(id);
        }

        private ImageModel saveInDatabase(ImageModel imageModel) {
                return imageRepository.save(imageModel);
        }

        private ImageModel findAndValidateByMusicId(int id) {
                ImageModel imageModel = imageRepository.findByMusicId(id);
                imageValidator.validateIfImageIsNotNull(imageModel);
                return imageModel;
        }

        private ImageModel findAndValidateByImageId(int id) {
                ImageModel imageModel = imageRepository.findByMusicId(id);
                imageValidator.validateIfImageIsNotNull(imageModel);
                return imageModel;
        }
}
