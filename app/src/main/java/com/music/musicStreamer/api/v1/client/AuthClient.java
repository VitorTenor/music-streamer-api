package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.entity.user.UserAuthRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthClient {

    private final AuthenticationManager authenticationManager;

    public Authentication authenticate(UserAuthRequest userAuthRequest) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(userAuthRequest.getEmail(), userAuthRequest.getPassword());
        return authenticationManager.authenticate(usernamePassword);
    }

}
