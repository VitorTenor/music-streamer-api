package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.user.User;
import com.music.musicStreamer.entity.user.UserAuth;
import com.music.musicStreamer.entity.user.UserAuthRequest;
import com.music.musicStreamer.entity.user.UserRequest;

public interface UserGateway {
    public User createUser(UserRequest userRequest);
    public UserAuth loginUser(UserAuthRequest userAuthRequest);
}
