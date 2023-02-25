package com.music.musicStreamer.api.v1.repositories;

import com.music.musicStreamer.domain.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<UserModel> findByEmail(String email);

}
