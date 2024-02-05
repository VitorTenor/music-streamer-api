package com.music.musicStreamer.api.v1.controller;

import com.google.gson.Gson;
import com.music.musicStreamer.core.security.model.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class AbstractController {

    private @Autowired HttpServletRequest httpServletRequest;
    private final Logger LOGGER = Logger.getLogger(AbstractController.class.getName());

    public UserToken getUserFromToken() {
        LOGGER.info("[AbstractController] Get user from token");

        var payload = tokenDecode(getToken());
        var gson = new Gson();
        var userToken = gson.fromJson(payload, UserToken.class);

        LOGGER.info("[AbstractController] User email: " + userToken.getUserEmail());
        LOGGER.info("[AbstractController] User id: " + userToken.getUserId());

        return userToken;
    }

    private static String tokenDecode(String token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] chunks = token.split("\\.");
        return new String(decoder.decode(chunks[1]));
    }

    private String getToken() {
        String token = httpServletRequest.getHeader("Authorization");
        return token.replace("Bearer ", "");
    }

    protected <T> ResponseEntity<T> buildResponseEntity(final HttpStatus status, final T response) {
        return ResponseEntity.status(status).body(response);
    }
}
