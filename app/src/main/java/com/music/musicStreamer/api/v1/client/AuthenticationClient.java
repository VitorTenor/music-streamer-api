package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.entity.user.LoginEntity;
import com.music.musicStreamer.gateway.AuthenticationGateway;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
@AllArgsConstructor
public class AuthenticationClient implements AuthenticationGateway {
    private final AuthenticationManager authenticationManager;

    public Boolean isAuthenticated(LoginEntity entity) {
        info(this.getClass(), "Authenticate user");

        var usernamePassword = new UsernamePasswordAuthenticationToken(entity.email(), entity.password());

        info(this.getClass(), "User email: " + entity.email());

        return authenticationManager.authenticate(usernamePassword).isAuthenticated();
    }

}
