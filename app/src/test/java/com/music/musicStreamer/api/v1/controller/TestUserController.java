package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.request.UserLogin;
import com.music.musicStreamer.api.v1.request.UserRegister;
import com.music.musicStreamer.entity.user.User;
import com.music.musicStreamer.entity.user.UserAuth;
import com.music.musicStreamer.usecase.user.CreateUserUseCase;
import com.music.musicStreamer.usecase.user.LoginUserUseCase;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UserController Class Test")
public class TestUserController {
    @Mock
    private CreateUserUseCase createUserUseCase;
    @Mock
    private LoginUserUseCase loginUserUseCase;

    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(loginUserUseCase, createUserUseCase);
    }

    @Test
    @Order(1)
    @DisplayName("001 - Test register() method")
    public void register_ValidUserRegisterDTO_ReturnsCreatedUser() {
        UserRegister userRegister = new UserRegister("John Doe", "johndoe@example.com", "password");
        User createdUser = new User(1, "John Doe", "johndoe@example.com");

        when(createUserUseCase.execute(any())).thenReturn(createdUser);

        ResponseEntity<User> response = userController.register(userRegister);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdUser, response.getBody());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test login() method")
    public void login_ValidUserLoginDTO_ReturnsUserAuth() {
        UserLogin userLogin = new UserLogin(
                "johndoe@example.com",
                "password"
        );
        UserAuth userAuth = new UserAuth(1, "John Doe", "johndoe@example.com", "token");

        when(loginUserUseCase.execute(any())).thenReturn(userAuth);

        ResponseEntity<UserAuth> response = userController.login(userLogin);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userAuth, response.getBody());
    }
}
