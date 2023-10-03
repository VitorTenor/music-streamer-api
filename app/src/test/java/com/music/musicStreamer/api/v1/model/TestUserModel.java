package com.music.musicStreamer.api.v1.model;

import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("TestUserModel Test")
public class TestUserModel {
    @Test
    @Order(1)
    @DisplayName("001 - Test Dto")
    public void testUser() {
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setName("Vitor");
        userModel.setEmail("vitor@email.com");
        userModel.setPassword("123456");
        userModel.setCreated_at(new Date());
        userModel.setUpdated_at(new Date());

        UserModel userModel1 = new UserModel();
        userModel1.setId(userModel.getId());
        userModel1.setName(userModel.getName());
        userModel1.setEmail(userModel.getEmail());
        userModel1.setPassword(userModel.getPassword());
        userModel1.setCreated_at(userModel.getCreated_at());
        userModel1.setUpdated_at(userModel.getUpdated_at());

        assertEquals(userModel1.getId(), userModel.getId());
        assertEquals(userModel1.getName(), userModel.getName());
        assertEquals(userModel1.getEmail(), userModel.getEmail());
        assertEquals(userModel1.getPassword(), userModel.getPassword());
        assertEquals(userModel1.getCreated_at(), userModel.getCreated_at());
        assertEquals(userModel1.getUpdated_at(), userModel.getUpdated_at());
    }
}