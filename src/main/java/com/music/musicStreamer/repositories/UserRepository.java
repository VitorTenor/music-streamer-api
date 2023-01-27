package com.music.musicStreamer.repositories;

import com.music.musicStreamer.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    @Query("SELECT u FROM UserModel u WHERE u.email = :email")
    UserModel findByEmail(String email);
}
