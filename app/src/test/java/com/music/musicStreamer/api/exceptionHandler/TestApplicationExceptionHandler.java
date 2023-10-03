package com.music.musicStreamer.api.exceptionHandler;

import com.music.musicStreamer.enums.ImageMessages;
import com.music.musicStreamer.enums.MusicMessages;
import com.music.musicStreamer.enums.PlaylistMessages;
import com.music.musicStreamer.enums.UserMessages;
import com.music.musicStreamer.exception.*;
import com.music.musicStreamer.exception.SecurityException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("ApplicationExceptionHandler Class Test")
public class TestApplicationExceptionHandler {

    @Mock
    private WebRequest mockRequest;

    @InjectMocks
    private ApplicationExceptionHandler exceptionHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("001 - handleUncaught test")
    public void testHandleUncaught() {
        Exception exception = new Exception("Test exception");

        ResponseEntity<Object> response = exceptionHandler.handleUncaught(exception, mockRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Problem problem = (Problem) response.getBody();
        assertEquals(LocalDateTime.now().getYear(), problem.getTimestamp().getYear());
    }

    @Test
    @Order(2)
    @DisplayName("002 - handleBusinessException test")
    public void testHandleBusinessException() {
        BusinessException exception = new BusinessException("Test exception");

        ResponseEntity<Object> response = exceptionHandler.handleBusinessException(exception, mockRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Problem problem = (Problem) response.getBody();
        assertEquals(LocalDateTime.now().getYear(), problem.getTimestamp().getYear());
        // Verifique os outros campos do objeto Problem, se necessário
    }

    @Test
    @Order(3)
    @DisplayName("003 - handleMusicException test")
    public void testHandlePlaylistException() {
        PlaylistException exception = new PlaylistException(PlaylistMessages.NOT_FOUND);

        ResponseEntity<Object> response = exceptionHandler.handlePlaylistException(exception, mockRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Problem problem = (Problem) response.getBody();
        assertEquals(LocalDateTime.now().getYear(), problem.getTimestamp().getYear());
    }

    @Test
    @Order(4)
    @DisplayName("004 - handleUserException test")
    public void testHandleUserException() {
        UserException exception = new UserException(UserMessages.NOT_FOUND);

        ResponseEntity<Object> response = exceptionHandler.handleUserException(exception, mockRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Problem problem = (Problem) response.getBody();
        assertEquals(LocalDateTime.now().getYear(), problem.getTimestamp().getYear());
    }

    @Test
    @Order(5)
    @DisplayName("005 - handleImageException test")
    public void testHandleImageException() {
        ImageException exception = new ImageException(ImageMessages.NOT_FOUND);

        ResponseEntity<Object> response = exceptionHandler.handleImageException(exception, mockRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Problem problem = (Problem) response.getBody();
        assertEquals(LocalDateTime.now().getYear(), problem.getTimestamp().getYear());
    }

    @Test
    @Order(6)
    @DisplayName("006 - handleSecurityException test")
    public void testHandleSecurityException() {
        SecurityException exception = new SecurityException("Test exception");

        ResponseEntity<Object> response = exceptionHandler.handleSecurityException(exception, mockRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Problem problem = (Problem) response.getBody();
        assertEquals(LocalDateTime.now().getYear(), problem.getTimestamp().getYear());
    }

    @Test
    @Order(7)
    @DisplayName("007 - handleMusicException_ShouldReturnErrorResponse")
    public void testHandleMusicException() {
        MusicException exception = new MusicException(MusicMessages.NOT_FOUND);

        ResponseEntity<Object> response = exceptionHandler.handleMusicException(exception, mockRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Problem problem = (Problem) response.getBody();
        assertEquals(LocalDateTime.now().getYear(), problem.getTimestamp().getYear());
    }
}
