package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.enumerators.ImageErrorMessage;
import com.music.musicStreamer.api.v1.models.ImageModel;
import com.music.musicStreamer.api.v1.repositories.ImageRepository;
import com.music.musicStreamer.core.GenerateName;
import com.music.musicStreamer.core.utils.Validators;
import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.entities.image.ImageRequest;
import com.music.musicStreamer.exceptions.ImageException;
import com.music.musicStreamer.gateways.ImageGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ImageClient implements ImageGateway {
        private static @Value("${storage.image.path}") String IMAGE_PATH;
        private static @Value("${storage.image.mediaType}") String IMAGE_TYPE;
        private static @Value("${storage.image.url}") String IMAGE_URL;
        private final GenerateName generateName;
        private final ImageRepository imageRepository;
        private final Validators validators;

        @Override
        @Transactional
        public Image saveImage(ImageRequest imageRequest) {
                validators.validateImage(imageRequest);

                String newFileName = generateName.randomName();

                saveInStorage(imageRequest, newFileName);

                ImageModel imageModel =
                        ImageModel
                                .builder()
                                .pathName(newFileName + IMAGE_TYPE)
                                .musicId(imageRequest.getId())
                                .created_at(new Date())
                                .updated_at(new Date())
                                .build();

                return saveInDatabase(imageModel).toEntity(IMAGE_URL);
        }

        @Override
        public byte[] getImage(String getPathName) {
                try {
                        return Files.readAllBytes(Paths.get(IMAGE_PATH + getPathName));
                } catch (IOException e) {
                        throw new ImageException(ImageErrorMessage.READ_ERROR);
                }
        }

        @Override
        public Image getImageById(int id) {
                ImageModel imageModel = findByMusicId(id);
                if (imageModel == null) throw new ImageException(ImageErrorMessage.NOT_FOUND);

                return new Image(imageModel.getMusicId(), imageModel.getPathName(),IMAGE_URL + imageModel.getPathName());
        }

        @Override
        @Transactional
        public Boolean deleteImageByMusicId(int id) {
                ImageModel imageModel = findByMusicId(id);
                validators.validateIfImageIsNotNull(imageModel);

                deleteInStorage(imageModel.getPathName());
                deleteInDatabaseById(imageModel.getId());
                return true;
        }



        @Override
        public Image getImageByMusicId(int id) {
                ImageModel imageModel = findByMusicId(id);
                validators.validateIfImageIsNotNull(imageModel);

                return new Image(imageModel.getMusicId(), imageModel.getPathName(),IMAGE_URL + imageModel.getPathName());
        }

        private ImageModel findByMusicId(int id) {
                return imageRepository.findByMusicId(id);
        }

        private void deleteInDatabaseById(int id) {
                imageRepository.deleteById(id);
        }

        private void deleteInStorage(String getPathName) {
                try {
                        Files.delete(Paths.get(IMAGE_PATH + getPathName));
                } catch (IOException e) {
                        throw new ImageException(ImageErrorMessage.DELETE_STORAGE);
                }
        }

        private ImageModel saveInDatabase(ImageModel imageModel) {
                return imageRepository.save(imageModel);
        }

        private void saveInStorage(ImageRequest imageRequest, String newFileName) {
                Path path = Paths.get(IMAGE_PATH + newFileName + IMAGE_TYPE);
                try {
                        Files.write(path, imageRequest.getImage());
                } catch (IOException e) {
                        throw new ImageException(ImageErrorMessage.SAVE_STORAGE);
                }
        }

}
