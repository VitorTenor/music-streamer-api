package com.music.musicStreamer.core.token;

import com.music.musicStreamer.api.v1.models.UserModel;
import com.music.musicStreamer.api.v1.repositories.UserRepository;
import com.music.musicStreamer.enums.UserMessages;
import com.music.musicStreamer.exceptions.SecurityException;
import com.music.musicStreamer.exceptions.UserException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthService {

//    @Value("${jwt.secret}")
    private String jwtSecret = "abnidfndsjkcmdcjkvcklndda";

//    @Value("${jwt.expiration}")
    private long jwtExpiration = 999_000_000;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Logger logger = Logger.getLogger(AuthService.class.getName());


    public String authenticateUser(String username, String password) {
        logger.info("[AuthService] Authenticate user");
        UserDetails userDetails = authenticate(username, password);

        return generateToken(userDetails);
    }

    private String generateToken(UserDetails userDetails) {
        logger.info("[AuthService] Generate token");
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private UserDetails authenticate(String username, String password) {
        logger.info("[AuthService] Authenticate user");
        UserModel user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserException(UserMessages.NOT_FOUND));

        logger.info("[AuthService] User found => " + user.getEmail());

        if (passwordEncoder.matches(password, user.getPassword())) {
            logger.info("[AuthService] User authenticated => " + user.getEmail());
            return new User(username, user.getPassword(), new ArrayList<>());
        }

        throw new SecurityException("Invalid credentials");
    }
}
