package com.music.musicStreamer.api.v1.repository;


import com.music.musicStreamer.api.v1.model.MusicModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<MusicModel, Integer> {

    List<MusicModel> findAllById(int musicId);
}

