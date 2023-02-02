package com.music.musicStreamer.domain.repositories;

import com.music.musicStreamer.domain.models.PlaylistMusicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicModel, Integer> {
    @Query(value = "SELECT * FROM playlist_musics WHERE playlist_id = :id", nativeQuery = true)
    ArrayList<PlaylistMusicModel> findByPlaylistId2(int id);

    @Query(value = "SELECT * FROM playlist_musics WHERE music_id = :id", nativeQuery = true)
    List<PlaylistMusicModel> findByMusicId(Integer id);
}
