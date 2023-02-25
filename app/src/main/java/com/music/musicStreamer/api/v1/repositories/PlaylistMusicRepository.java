package com.music.musicStreamer.api.v1.repositories;

import com.music.musicStreamer.api.v1.models.PlaylistMusicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicModel, Integer> {
    List<PlaylistMusicModel> findByMusicId(Integer id);
    List<PlaylistMusicModel> findAllById(int id);
}
