package com.music.musicStreamer.domain.repositories;

import com.music.musicStreamer.domain.models.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<ImageModel, Integer> {

    @Query(value = "SELECT * FROM images WHERE music_id = :id", nativeQuery = true)
    ImageModel findByMusicId(Integer id);

}

