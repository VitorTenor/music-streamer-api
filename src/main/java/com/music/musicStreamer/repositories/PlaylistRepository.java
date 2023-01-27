package com.music.musicStreamer.repositories;

import com.music.musicStreamer.models.PlaylistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;


public interface PlaylistRepository extends JpaRepository<PlaylistModel, Integer> {
    @Query(value = "SELECT * FROM playlists WHERE user_id = :userId", nativeQuery = true)
    ArrayList<PlaylistModel> findAllByUserId(int userId);
}
