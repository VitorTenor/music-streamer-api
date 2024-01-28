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
@Sql(
        scripts = {
                "classpath:sql/delete.sql",
                "classpath:sql/insertUserControllerTest.sql"
        }
)
public class TestUserController extends AbstractContextTest {

    private final String PATH = "/v1/users";
    private final String PATH_REGISTER = "/register";

    @Test
    @Order(1)
    @DisplayName("001 - Test user register - success")
    void test001() throws Exception {
        // arrange
        var jsonRequst =
                """
                            {
                                "name": "Vitor",
                                "password": "123456",
                                "email": "vitor@vitor.com"
                            }
                        """;

        var request = MockMvcRequestBuilders
                .post(PATH + PATH_REGISTER)
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
        var jsonRequst =
                """
                            {
                                "name": "Vitor",
                                "password": "123456"
                            }
                        """;

        var request = MockMvcRequestBuilders
                .post(PATH + PATH_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequst);

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var error = this.payloadExtractor.as(Problem.class);

        assertNotNull(error);
        assertEquals(INVALID_FIELD_TITLE, error.getTitle());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getDetail());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getUserMessage());
        assertEquals("email", error.getObjects().get(0).getName());
        assertEquals(String.format(REQUIRED_FIELD, "Email"), error.getObjects().get(0).getUserMessage());
    }

    @Test
    @Order(3)
    @DisplayName("003 - Test user register - withouth name")
    void test003() throws Exception {
        // arrange
        var jsonRequst =
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

        var error = this.payloadExtractor.as(Problem.class);

        assertNotNull(error);
        assertEquals(INVALID_FIELD_TITLE, error.getTitle());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getDetail());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getUserMessage());
        assertEquals("name", error.getObjects().get(0).getName());
        assertEquals(String.format(REQUIRED_FIELD, "Name"), error.getObjects().get(0).getUserMessage());
    }

    @Test
    @Order(4)
    @DisplayName("004 - Test user register - withouth password")
    void test004() throws Exception {
        // arrange
        var jsonRequst =
                """
                           {
                                "name": "Vitor",
                                "email": "vitor@vitor.com"
                           }
                        """;

        var request = MockMvcRequestBuilders
                .post(PATH + PATH_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequst);

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var error = this.payloadExtractor.as(Problem.class);

        assertNotNull(error);
        assertEquals(INVALID_FIELD_TITLE, error.getTitle());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getDetail());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getUserMessage());
        assertEquals("password", error.getObjects().get(0).getName());
        assertEquals(String.format(REQUIRED_FIELD, "Password"), error.getObjects().get(0).getUserMessage());
    }

    @Test
    @Order(5)
    @DisplayName("005 - Test user register - with existing email")
    void test005() throws Exception {
        // arrange
        var jsonRequst =
                """
                            {
                                "name": "Vitor",
                                "password": "123456",
                                "email": "email@email.com"
                            }
                        """;

        var request = MockMvcRequestBuilders
                .post(PATH + PATH_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequst);

        // act

        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var error = this.payloadExtractor.as(Problem.class);

        assertNotNull(error);
        assertEquals("User error", error.getTitle());
        assertEquals("User already exists", error.getDetail());
        assertEquals("User already exists", error.getUserMessage());
    }

    @Test
    @Order(6)
    @DisplayName("006 - Test user register - with invalid email")
    void test006() throws Exception {
        // arrange
        var jsonRequst =
                """
                            {
                                "name": "Vitor",
                                "password": "123456",
                                "email": "email"
                            }
                        """;

        var request = MockMvcRequestBuilders
                .post(PATH + PATH_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequst);

        // act

        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var error = this.payloadExtractor.as(Problem.class);

        assertNotNull(error);
        assertEquals(INVALID_FIELD_TITLE, error.getTitle());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getDetail());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getUserMessage());
        assertEquals("email", error.getObjects().get(0).getName());
        assertEquals(String.format(INVALID_FIELD, "Email"), error.getObjects().get(0).getUserMessage());
    }

    @Test
    @Order(7)
    @DisplayName("007 - Test user register - with password too short")
    void test007() throws Exception {
        // arrange
        var jsonRequst =
                """
                                    {
                                        "name": "Vitor",
                                        "password": "123",
                                        "email": "email@email.com"
                                    }
                        """;

        var request = MockMvcRequestBuilders
                .post(PATH + PATH_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequst);

        var expectedMessage = "Password must be at least 6 characters";

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var error = this.payloadExtractor.as(Problem.class);

        assertNotNull(error);
        assertEquals(INVALID_FIELD_TITLE, error.getTitle());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getDetail());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getUserMessage());
        assertEquals("password", error.getObjects().get(0).getName());
        assertEquals(expectedMessage, error.getObjects().get(0).getUserMessage());
    }
}
