package com.music.musicStreamer.domain.repositories;

import com.music.musicStreamer.domain.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    @Query(value = "SELECT * FROM users u WHERE u.email = :email", nativeQuery = true)
    UserModel findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<UserModel> findByEmailForAuthenticate(String email);

}
