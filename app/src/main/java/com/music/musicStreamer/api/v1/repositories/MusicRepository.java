package com.music.musicStreamer.api.v1.repositories;


import com.music.musicStreamer.api.v1.models.MusicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicRepository extends JpaRepository<MusicModel, Integer> {

    @Query(value = "SELECT * FROM musics WHERE id = :musicId", nativeQuery = true)
    List<MusicModel> findAllMusicById(int musicId);
}

