package com.music.musicStreamer.api.v1.models.dtos;

import com.music.musicStreamer.entities.user.UserAuthRequest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("TestUserLoginDTO Test")
public class TestUserLoginDTO {

    @Test
    @Order(1)
    @DisplayName("001 - Test Dto")
    public void testUserLogin(){
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("vitor@teste.com");
        userLoginDTO.setPassword("123456");

        UserLoginDTO userLoginDTO1 = new UserLoginDTO();
        userLoginDTO1.setEmail(userLoginDTO.getEmail());
        userLoginDTO1.setPassword(userLoginDTO.getPassword());

        assertEquals(userLoginDTO1.getEmail(), userLoginDTO.getEmail());
        assertEquals(userLoginDTO1.getPassword(), userLoginDTO.getPassword());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test toEntity method")
    public void testToEntity() {
        String email = "vitor@teste.com";
        String password = "123456";

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(email);
        userLoginDTO.setPassword(password);

        assertEquals(email, userLoginDTO.getEmail());
        assertEquals(password, userLoginDTO.getPassword());

        UserAuthRequest request = userLoginDTO.toEntity();

        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());


    }


}