package com.music.musicStreamer.core.token.data;

import com.music.musicStreamer.domain.models.UserModel;
import com.music.musicStreamer.api.v1.models.dtos.UserLoginDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserDataDetail implements UserDetails {

    private final Optional<UserModel> usuario;

    public UserDataDetail(Optional<UserModel> usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return usuario.orElse(new UserModel()).getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.orElse(new UserModel()).getEmail();
    }

    public UserLoginDTO getUserDTO() {
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setEmail(usuario.orElse(new UserModel()).getEmail());
        userDTO.setEmail(usuario.orElse(new UserModel()).getEmail());
        return userDTO;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
