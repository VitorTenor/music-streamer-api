package com.music.musicStreamer.core.token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    String[] allowedUrls = {
            "/music-streamer/v1/users",
            "/music-streamer/v1/musics",
            "/music-streamer/v1/musics/play/**",
            "/music-streamer/v1/users/**",
            "/swagger-ui/**",
            "/**",
            "/swagger-resources/**",
            "/v2/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(allowedUrls)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .httpBasic().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
