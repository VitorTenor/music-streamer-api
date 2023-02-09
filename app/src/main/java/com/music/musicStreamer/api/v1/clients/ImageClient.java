package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.ImageModel;
import com.music.musicStreamer.api.v1.repositories.ImageRepository;
import com.music.musicStreamer.api.v1.repositories.MusicRepository;
import com.music.musicStreamer.core.GenerateName;
import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.entities.image.ImageRequest;
import com.music.musicStreamer.gateways.ImageGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ImageClient implements ImageGateway {
        private @Value("${storage.image.path}") String IMAGE_PATH;
        private @Value("${storage.image.mediaType}") String IMAGE_TYPE;
        private @Value("${storage.image.url}") String IMAGE_URL;
        private final GenerateName generateName;
        private final ImageRepository imageRepository;
        private final MusicRepository musicRepository;

        @Override
        public void saveImage(ImageRequest imageRequest) {
                try {
                        if(musicRepository.existsById(imageRequest.getId())) {
                                ImageModel imageModel = new ImageModel();
                                String newFileName = generateName.randomName();
                                Path path = Paths.get(IMAGE_PATH + newFileName + IMAGE_TYPE);
                                Files.write(path, imageRequest.getImage());
                                imageModel.setPathName(newFileName + IMAGE_TYPE);
                                imageModel.setMusicId(imageRequest.getId());
                                imageModel.setCreated_at(new Date());
                                imageModel.setUpdated_at(new Date());
                                imageRepository.save(imageModel);
                        } else {
                                throw new IOException("Music not found or already has an image");
                        }
                }
                catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }
        @Override
        public byte[] getImage(String getPathName) {
                try {
                        Path path = Paths.get(IMAGE_PATH + getPathName);
                        return Files.readAllBytes(path);
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }
        @Override
        public Image getImageById(int id) {
                ImageModel imageModel = imageRepository.findByMusicId(id);
                if (imageModel == null) {
                        return null;
                }
                return new Image(imageModel.getMusicId(), imageModel.getPathName(),IMAGE_URL + imageModel.getPathName());
        }
        @Override
        public Boolean deleteImageByMusicId(int id) {
                ImageModel imageModel = imageRepository.findByMusicId(id);
                if (imageModel == null) {
                        return false;
                }
                try {
                        Path path = Paths.get(IMAGE_PATH + imageModel.getPathName());
                        Files.delete(path);
                        imageRepository.deleteById(imageModel.getId());
                        return true;
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }
        @Override
        public Image getImageByMusicId(int id) {
                ImageModel imageModel = imageRepository.findByMusicId(id);
                if (imageModel == null) {
                        return null;
                }
                return new Image(imageModel.getMusicId(), imageModel.getPathName(),IMAGE_URL + imageModel.getPathName());
        }
}
