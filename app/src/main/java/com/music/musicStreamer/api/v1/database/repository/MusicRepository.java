package com.music.musicStreamer.api.v1.database.repository;


import com.music.musicStreamer.api.v1.database.model.MusicModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<MusicModel, Integer> {

}

