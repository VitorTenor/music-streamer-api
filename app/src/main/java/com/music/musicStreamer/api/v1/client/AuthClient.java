package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.entity.user.UserAuthRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class AuthClient {

    private final AuthenticationManager authenticationManager;
    private final Logger LOGGER = Logger.getLogger(AuthClient.class.getName());
    public Authentication authenticate(UserAuthRequest userAuthRequest) {
        LOGGER.info("[AuthClient] Authenticate user");

        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(userAuthRequest.getEmail(), userAuthRequest.getPassword());

        LOGGER.info("[AuthClient] User email: " + userAuthRequest.getEmail());

        return authenticationManager.authenticate(usernamePassword);
    }

}
