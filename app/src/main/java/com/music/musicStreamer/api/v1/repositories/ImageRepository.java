package com.music.musicStreamer.api.v1.repositories;

import com.music.musicStreamer.api.v1.models.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<ImageModel, Integer> {

    @Query(value = "SELECT * FROM images WHERE music_id = :id", nativeQuery = true)
    ImageModel findByMusicId(Integer id);

}

