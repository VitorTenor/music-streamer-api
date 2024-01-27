package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.response.UserRegisterResponse;
import com.music.musicStreamer.util.AbstractContextTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration tests for UserController")
@Sql({
        "classpath:sql/delete.sql",
})
public class TestUserController extends AbstractContextTest {

    private final String PATH = "/v1/users";

    @Test
    @Order(1)
    @DisplayName("001 - Test user register - success")
    void test001() throws Exception {
        // arrange
        var jsonRequst=
                """
                    {
                        "name": "Vitor",
                        "password": "123456",
                        "email": "vitor@vitor.com"
                    }
                """;

        var request = MockMvcRequestBuilders
                .post(PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequst);

        // act

        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        var user = this.payloadExtractor.as(UserRegisterResponse.class);

        assertNotNull(user);
        assertEquals("vitor@vitor.com", user.getEmail());
        assertEquals("Vitor", user.getName());
    }


}
