package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.user.UserRegisterResponseEntity;
import com.music.musicStreamer.entity.user.UserAuth;
import com.music.musicStreamer.entity.user.UserAuthRequest;
import com.music.musicStreamer.entity.user.UserRegisterRequestEntity;

public interface UserGateway {
    public UserRegisterResponseEntity createUser(UserRegisterRequestEntity userRegisterRequestEntity);
    public UserAuth loginUser(UserAuthRequest userAuthRequest);
}
