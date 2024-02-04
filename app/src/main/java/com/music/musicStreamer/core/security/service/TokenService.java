package com.music.musicStreamer.core.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.music.musicStreamer.api.v1.database.model.UserModel;
import com.music.musicStreamer.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(UserModel userModel){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(userModel.getEmail())
                    .withClaim("userId", userModel.getId())
                    .withClaim("userEmail", userModel.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            throw new BusinessException("Error while generating token");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception exception){
            return "";
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime
                .now().plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
