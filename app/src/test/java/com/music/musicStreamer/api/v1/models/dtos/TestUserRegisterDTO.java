package com.music.musicStreamer.api.v1.models.dtos;

import com.music.musicStreamer.entities.user.UserRequest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("TestUserRegisterDTO Test")
public class TestUserRegisterDTO {
    @Test
    @Order(1)
    @DisplayName("001 - Test Dto")
    public void testUserRegisterDto(){

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("vitor", "vitor@email.com", "123456");

        UserRegisterDTO userRegisterDTO1 = new UserRegisterDTO(
            userRegisterDTO.getName(),
            userRegisterDTO.getEmail(),
            userRegisterDTO.getPassword()
        );

        assertEquals(userRegisterDTO1.getName(), userRegisterDTO.getName());
        assertEquals(userRegisterDTO1.getEmail(), userRegisterDTO.getEmail());

        userRegisterDTO.setEmail(null);
        userRegisterDTO.setName(null);
        userRegisterDTO.setPassword(null);

        assertNotEquals(userRegisterDTO1.getName(), userRegisterDTO.getName());
        assertNotEquals(userRegisterDTO1.getEmail(), userRegisterDTO.getEmail());
        assertNotEquals(userRegisterDTO1.getPassword(), userRegisterDTO.getPassword());

        assertEquals(null, userRegisterDTO.getName());
        assertEquals(null, userRegisterDTO.getEmail());
        assertEquals(null, userRegisterDTO.getPassword());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test toEntity method")
    public void testToEntity() {
        String name = "vitor";
        String email = "vitor@teste.com";
        String password = "123456";

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(name, email, password);
        UserRequest request = userRegisterDTO.toEntity();

        assertEquals(userRegisterDTO.getName(), name);
        assertEquals(userRegisterDTO.getEmail(), email);
        assertEquals(userRegisterDTO.getPassword(), password);

        assertEquals(request.getName(), name);
        assertEquals(request.getEmail(), email);
        assertEquals(request.getPassword(), password);

        assertEquals(request.getName(), userRegisterDTO.getName());
        assertEquals(request.getEmail(), userRegisterDTO.getEmail());
        assertEquals(request.getPassword(), userRegisterDTO.getPassword());
    }
}