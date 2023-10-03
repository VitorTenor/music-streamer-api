package com.music.musicStreamer.api.v1.repository;

import com.music.musicStreamer.api.v1.model.PlaylistMusicModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicModel, Integer> {
    List<PlaylistMusicModel> findByMusicId(Integer id);
    List<PlaylistMusicModel> findAllById(int id);
}
