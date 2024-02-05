package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.entity.user.LoginEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class AuthClient {

    private final AuthenticationManager authenticationManager;
    private final Logger LOGGER = Logger.getLogger(AuthClient.class.getName());

    public Boolean authenticate(LoginEntity entity) {
        LOGGER.info("[AuthClient] Authenticate user");

        var usernamePassword = new UsernamePasswordAuthenticationToken(entity.email(), entity.password());

        LOGGER.info("[AuthClient] User email: " + entity.email());

        return authenticationManager.authenticate(usernamePassword).isAuthenticated();
    }

}
