package com.music.musicStreamer.api.v1.clients;

import com.music.musicStreamer.api.v1.models.ImageModel;
import com.music.musicStreamer.api.v1.repositories.ImageRepository;
import com.music.musicStreamer.api.v1.repositories.MusicRepository;
import com.music.musicStreamer.core.GenerateName;
import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.entities.image.ImageRequest;
import com.music.musicStreamer.exception.ImageException;
import com.music.musicStreamer.exception.MusicException;
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
        public Image saveImage(ImageRequest imageRequest) {
                validateImage(imageRequest);
                try {
                        ImageModel imageModel = new ImageModel();
                        String newFileName = generateName.randomName();
                        Path path = Paths.get(IMAGE_PATH + newFileName + IMAGE_TYPE);
                        Files.write(path, imageRequest.getImage());
                        imageModel.setPathName(newFileName + IMAGE_TYPE);
                        imageModel.setMusicId(imageRequest.getId());
                        imageModel.setCreated_at(new Date());
                        imageModel.setUpdated_at(new Date());
                        ImageModel imageModel01 = imageRepository.save(imageModel);
                        return imageModel01.toEntity(IMAGE_URL);
                } catch (Exception e) {
                        throw new ImageException("Error to save image");
                }
        }

        private void validateImage(ImageRequest imageRequest) {
                if (imageRequest.getImage() == null) throw new ImageException("Image is required");
                if (imageRequest.getId() == 0) throw new ImageException("Id is required");
                if (!musicRepository.existsById(imageRequest.getId())) throw new MusicException("Music not found");
        }


        @Override
        public byte[] getImage(String getPathName) {
                try {
                        return Files.readAllBytes(Paths.get(IMAGE_PATH + getPathName));
                } catch (IOException e) {
                        throw new ImageException( "Error to get image");
                }
        }
        @Override
        public Image getImageById(int id) {
                ImageModel imageModel = imageRepository.findByMusicId(id);
                if (imageModel == null) throw new ImageException( "Image not found");
                return new Image(imageModel.getMusicId(), imageModel.getPathName(),IMAGE_URL + imageModel.getPathName());
        }
        @Override
        public Boolean deleteImageByMusicId(int id) {
                ImageModel imageModel = imageRepository.findByMusicId(id);
                if (imageModel == null) throw new ImageException( "Image not found");
                try {
                        Files.delete(Paths.get(IMAGE_PATH + imageModel.getPathName()));
                        imageRepository.deleteById(imageModel.getId());
                        return true;
                } catch (IOException e) {
                        throw new ImageException( "Error to delete image");
                }
        }
        @Override
        public Image getImageByMusicId(int id) {
                ImageModel imageModel = imageRepository.findByMusicId(id);
                if (imageModel == null) throw new ImageException( "Image not found");
                return new Image(imageModel.getMusicId(), imageModel.getPathName(),IMAGE_URL + imageModel.getPathName());
        }
}
