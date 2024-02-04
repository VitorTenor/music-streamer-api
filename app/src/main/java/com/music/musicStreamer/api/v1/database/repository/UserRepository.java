package com.music.musicStreamer.api.v1.database.repository;

import com.music.musicStreamer.api.v1.database.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<UserModel> findByEmail(String email);
    Boolean existsByEmail(String email);
    UserDetails findUserDetailsByEmail(String email);
}
