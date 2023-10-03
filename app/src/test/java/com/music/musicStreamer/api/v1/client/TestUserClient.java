package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.model.UserModel;
import com.music.musicStreamer.api.v1.repository.UserRepository;
import com.music.musicStreamer.core.feign.Auth;
import com.music.musicStreamer.core.util.factory.UserFactory;
import com.music.musicStreamer.core.util.validator.UserValidator;
import com.music.musicStreamer.entity.user.User;
import com.music.musicStreamer.entity.user.UserAuth;
import com.music.musicStreamer.entity.user.UserAuthRequest;
import com.music.musicStreamer.entity.user.UserRequest;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UserClient Class Test")
public class TestUserClient {
    @Mock
    private Auth auth;
    @Mock
    private UserFactory userFactory;
    @Mock
    private UserValidator userValidator;
    @Mock
    private UserRepository userRepository;

    private UserClient userClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userClient = new UserClient(auth, userFactory, userValidator, userRepository);
    }

    @Test
    @Order(1)
    @DisplayName("001 - Test createUser() method ")
    public void testCreateUser() {
        // Arrange
        UserRequest userRequest = new UserRequest("John Doe", "johndoe@example.com", "password");
        UserModel createdUserModel = new UserModel();
        createdUserModel.setId(1);
        createdUserModel.setEmail("johndoe@example.com");
        createdUserModel.setName("John Doe");
        User createdUser = new User(1, "John Doe", "johndoe@example.com");

        when(userFactory.createUserModel(userRequest)).thenReturn(createdUserModel);
        when(userRepository.save(createdUserModel)).thenReturn(createdUserModel);
        when(userFactory.createUser(createdUserModel)).thenReturn(createdUser);

        User result = userClient.createUser(userRequest);

        assertEquals(createdUser, result);
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test loginUser() method ")
    public void testLoginUser() {
        UserAuthRequest userAuthRequest = new UserAuthRequest("johndoe@example.com", "password");
        UserModel createdUserModel = new UserModel();
        createdUserModel.setId(1);
        createdUserModel.setEmail("johndoe@example.com");
        createdUserModel.setName("John Doe");
        UserAuth expectedUserAuth = new UserAuth(1, "John Doe", "johndoe@example.com", "token");

        when(userValidator.validateUserLogin(userAuthRequest)).thenReturn(createdUserModel);
        when(auth.getToken(userAuthRequest)).thenReturn("token");

        UserAuth result = userClient.loginUser(userAuthRequest);

        assertEquals(expectedUserAuth.getId(), result.getId());
        assertEquals(expectedUserAuth.getName(), result.getName());
        assertEquals(expectedUserAuth.getEmail(), result.getEmail());
    }
}
