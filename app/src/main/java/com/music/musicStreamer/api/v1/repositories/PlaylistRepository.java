package com.music.musicStreamer.api.v1.repositories;

import com.music.musicStreamer.domain.models.PlaylistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;


public interface PlaylistRepository extends JpaRepository<PlaylistModel, Integer> {
    @Query(value = "SELECT * FROM playlists WHERE user_id = :userId", nativeQuery = true)
    List<PlaylistModel> findAllByUserId(int userId);
}
