package com.music.musicStreamer.core.token.services;


import com.music.musicStreamer.domain.models.UserModel;
import com.music.musicStreamer.api.v1.repositories.UserRepository;
import com.music.musicStreamer.core.token.data.UserDataDetail;
import com.music.musicStreamer.exceptions.UserException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserModel> user = repository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        return new UserDataDetail(user);
    }

}
