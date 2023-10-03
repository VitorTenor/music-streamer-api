package com.music.musicStreamer.api.v1.models.dtos;

import com.music.musicStreamer.api.v1.request.UserRegister;
import com.music.musicStreamer.entities.user.UserRequest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("TestUserRegister Test")
public class TestUserRegister {
    @Test
    @Order(1)
    @DisplayName("001 - Test Constructor")
    public void testUserRegisterConstructor(){

        UserRegister userRegister = new UserRegister("vitor", "vitor@email.com", "123456");

        UserRegister userRegister1 = new UserRegister(
            userRegister.name(),
            userRegister.email(),
            userRegister.password()
        );

        assertEquals(userRegister.name(), userRegister1.name());
        assertEquals(userRegister.email(), userRegister1.email());
        assertEquals(userRegister.password(), userRegister1.password());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test toEntity method")
    public void testToEntity() {
        String name = "vitor";
        String email = "vitor@teste.com";
        String password = "123456";

        UserRegister userRegister = new UserRegister(name, email, password);
        UserRequest request = userRegister.toEntity();

        assertEquals(userRegister.name(), name);
        assertEquals(userRegister.email(), email);
        assertEquals(userRegister.password(), password);

        assertEquals(request.getName(), name);
        assertEquals(request.getEmail(), email);
        assertEquals(request.getPassword(), password);

        assertEquals(request.getName(), userRegister.name());
        assertEquals(request.getEmail(), userRegister.email());
        assertEquals(request.getPassword(), userRegister.password());
    }
}