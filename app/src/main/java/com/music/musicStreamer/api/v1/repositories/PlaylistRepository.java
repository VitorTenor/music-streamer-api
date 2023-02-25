package com.music.musicStreamer.api.v1.repositories;

import com.music.musicStreamer.api.v1.models.PlaylistModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PlaylistRepository extends JpaRepository<PlaylistModel, Integer> {
    List<PlaylistModel> findAllByUserId(int userId);
}
