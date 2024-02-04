package com.music.musicStreamer.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class JwtUtil {
    public static String generateToken() {
        final var algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuer("auth0")
                .withSubject("email@email.com")
                .withClaim("userId", 0)
                .withClaim("userEmail", "email@email.com")
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);

    }

    private static Instant generateExpirationDate() {
        return LocalDateTime
                .now().plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
