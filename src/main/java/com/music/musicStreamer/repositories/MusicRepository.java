package com.music.musicStreamer.repositories;

import com.music.musicStreamer.models.MusicModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<MusicModel, Integer> {
}

