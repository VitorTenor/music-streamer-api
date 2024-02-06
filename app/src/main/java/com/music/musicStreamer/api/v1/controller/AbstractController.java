package com.music.musicStreamer.api.v1.controller;

import com.google.gson.Gson;
import com.music.musicStreamer.core.security.model.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
public class AbstractController {

    private @Autowired HttpServletRequest httpServletRequest;
    private final String TOKEN_PREFIX = "Bearer";
    
    public UserToken getUserFromToken() {
        info(this.getClass(), "Get user from token");

        var payload = tokenDecode(getToken());
        var userToken = new Gson().fromJson(payload, UserToken.class);

        info(this.getClass(), "User email: " + userToken.getUserEmail());
        info(this.getClass(), "User id: " + userToken.getUserId());

        return userToken;
    }

    private static String tokenDecode(final String token) {
        var decoder = Base64.getUrlDecoder();
        var chunks = token.split("\\.");
        return new String(decoder.decode(chunks[1]));
    }

    private String getToken() {
        var token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        return token.replace(TOKEN_PREFIX, "");
    }

    protected <T> ResponseEntity<T> buildResponseEntity(final HttpStatus status, final T response) {
        return ResponseEntity.status(status).body(response);
    }

    protected <T> ResponseEntity<T> buildResponseEntity(final HttpStatus status, final MediaType mediaType, final T response) {
        return ResponseEntity.status(status).contentType(mediaType).body(response);
    }
}
