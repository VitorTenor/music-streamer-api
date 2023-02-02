package com.music.musicStreamer.domain.utils.token.services;


import com.music.musicStreamer.domain.models.UserModel;
import com.music.musicStreamer.domain.repositories.UserRepository;
import com.music.musicStreamer.domain.utils.token.data.UserDataDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = repository.findByEmailForAuthenticate(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User [" + username + "] not found");
        }

        return new UserDataDetail(user);
    }

}
