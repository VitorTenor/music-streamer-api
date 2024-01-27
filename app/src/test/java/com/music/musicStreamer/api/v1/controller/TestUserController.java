package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.exceptionHandler.Problem;
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
@Sql(value = "/sql/delete.sql")
public class TestUserController extends AbstractContextTest {

    private final String PATH = "/v1/users";
    private final String INVALID_FIELD = "Invalid field";
    private final String ONE_OR_MORE_FIELDS_ARE_INVALID = "One or more fields are invalid. Fill in correctly and try again.";

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
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

        var user = this.payloadExtractor.as(UserRegisterResponse.class);

        assertNotNull(user);
        assertEquals("Vitor", user.getName());
        assertEquals("vitor@vitor.com", user.getEmail());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test user register - withouth email")
    void test002() throws Exception {
        // arrange
        var jsonRequst=
                """
                    {
                        "name": "Vitor",
                        "password": "123456"
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
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var errors = this.payloadExtractor.as(Problem.class);

        assertNotNull(errors);
        assertEquals(INVALID_FIELD, errors.getTitle());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, errors.getDetail());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, errors.getUserMessage());
        assertEquals("email", errors.getObjects().get(0).getName());
        assertEquals("Email is required", errors.getObjects().get(0).getUserMessage());
    }

    @Test
    @Order(3)
    @DisplayName("003 - Test user register - withouth name")
    void test003() throws Exception {
        // arrange
        var jsonRequst=
                """
                    {
                        "email": "vitor@vitor.com",
                        "password": "123456"
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
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var errors = this.payloadExtractor.as(Problem.class);

        assertNotNull(errors);
        assertEquals(INVALID_FIELD, errors.getTitle());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, errors.getDetail());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, errors.getUserMessage());
        assertEquals("name", errors.getObjects().get(0).getName());
        assertEquals("Name is required", errors.getObjects().get(0).getUserMessage());
    }


}
