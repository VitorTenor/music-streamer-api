package com.music.musicStreamer.api.v1.database.repository;

import com.music.musicStreamer.api.v1.database.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageModel, Integer> {
    ImageModel findByMusicId(Integer id);

    Boolean existsByMusicId(int id);
}

