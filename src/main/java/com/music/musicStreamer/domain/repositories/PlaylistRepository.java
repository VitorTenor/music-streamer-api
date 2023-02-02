package com.music.musicStreamer.domain.repositories;

import com.music.musicStreamer.domain.models.PlaylistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;


public interface PlaylistRepository extends JpaRepository<PlaylistModel, Integer> {
    @Query(value = "SELECT * FROM playlists WHERE user_id = :userId", nativeQuery = true)
    ArrayList<PlaylistModel> findAllByUserId(int userId);
}
