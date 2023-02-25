package com.music.musicStreamer.gateways;

import com.music.musicStreamer.entities.user.User;
import com.music.musicStreamer.entities.user.UserAuth;
import com.music.musicStreamer.entities.user.UserAuthRequest;
import com.music.musicStreamer.entities.user.UserRequest;

public interface UserGateway {
    public User createUser(UserRequest userRequest);
    public UserAuth loginUser(UserAuthRequest userAuthRequest);
}
