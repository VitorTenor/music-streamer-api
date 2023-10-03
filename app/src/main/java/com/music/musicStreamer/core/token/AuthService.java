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

@Service
@RequiredArgsConstructor
public class AuthService {

//    @Value("${jwt.secret}")
    private String jwtSecret = "abnidfndsjkcmdcjkvcklndda";

//    @Value("${jwt.expiration}")
    private long jwtExpiration = 999_000_000;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public String authenticateUser(String username, String password) {
        UserDetails userDetails = authenticate(username, password);
        return generateToken(userDetails);
    }

    private String generateToken(UserDetails userDetails) {
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
        UserModel user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserException(UserMessages.NOT_FOUND));

        String hashedPassword = passwordEncoder.encode(password);

        if (passwordEncoder.matches(user.getPassword(), hashedPassword)) {
            return new User(username, hashedPassword, new ArrayList<>());
        }
        throw new SecurityException("Invalid credentials");
    }
}
