package com.music.musicStreamer.api.v1.model.dtos;

import com.music.musicStreamer.api.v1.request.UserLogin;
import com.music.musicStreamer.entities.user.UserAuthRequest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("TestUserLoginDTO Test")
public class TestUserLogin {

    @Test
    @Order(1)
    @DisplayName("001 - Test constructor")
    public void testUserLogin(){
        UserLogin userLogin = new UserLogin(
                "vitor@teste.com",
                "123456"
        );

        UserLogin userLogin1 = new UserLogin(
                userLogin.email(),
                userLogin.password()
        );

        assertEquals(userLogin1.email(), userLogin.email());
        assertEquals(userLogin1.password(), userLogin.password());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test toEntity method")
    public void testToEntity() {
        String email = "vitor@teste.com";
        String password = "123456";

        UserLogin userLogin = new UserLogin(
                email,
                password
        );

        assertEquals(email, userLogin.email());
        assertEquals(password, userLogin.password());

        UserAuthRequest request = userLogin.toEntity();

        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());


    }


}