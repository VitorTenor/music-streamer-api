package com.music.musicStreamer.domain.repositories;

import com.music.musicStreamer.domain.models.MusicModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<MusicModel, Integer> {
}

