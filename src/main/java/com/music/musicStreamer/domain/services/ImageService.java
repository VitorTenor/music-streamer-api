package com.music.musicStreamer.domain.services;

import com.music.musicStreamer.domain.models.ImageModel;
import com.music.musicStreamer.domain.models.dtos.ImageDTO;
import com.music.musicStreamer.domain.repositories.ImageRepository;
import com.music.musicStreamer.domain.utils.GenerateName;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final GenerateName generateName;
    public ImageService(ImageRepository imageRepository, GenerateName generateName) {
        this.imageRepository = imageRepository;
        this.generateName = generateName;
    }

    public ImageDTO getImageByMusicId(Integer id) {
        ImageModel imageModel = imageRepository.findByMusicId(id);
        if (imageModel == null) {
            return null;
        }
        return toImageDto(imageModel);
    }

    private ImageDTO toImageDto(ImageModel imageModel) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(imageModel.getId());
        imageDTO.setPath_name(imageModel.getPathName());
        imageDTO.setUrl("http://localhost:8081/images/" + imageModel.getPathName());
        return imageDTO;
    }
    public Object uploadImage(MultipartFile file, Integer id) throws IOException {
        ImageModel imageModel = imageRepository.findByMusicId(id);
        if (imageModel == null) {
            saveImage(file, id);
            return "Image registered";
        } else {
            return "Music not found or already has an image";
        }
    }

    private void saveImage(MultipartFile file, Integer id) throws IOException {
        ImageModel imageModel = new ImageModel();
        String newFileName = generateName.randomName();
        byte[] bytes = file.getBytes();
        Path path = Paths.get("src/main/resources/media/images/" + newFileName + ".jpg");
        Files.write(path, bytes);
        imageModel.setPathName(newFileName);
        imageModel.setMusicId(id);
        imageModel.setCreated_at(new Date());
        imageModel.setUpdated_at(new Date());
        imageRepository.save(imageModel);
    }

    public ResponseEntity<Object> getImage(String getPathName) throws IOException {
        byte[] image = Files.readAllBytes(Paths.get("src/main/resources/media/images/" + getPathName));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
