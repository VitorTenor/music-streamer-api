package com.music.musicStreamer.api.exceptionHandler;

import com.music.musicStreamer.exceptions.*;
import com.music.musicStreamer.exceptions.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.UNEXPECTED_ERROR;
        var problem = Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(problemType.getTitle())
                .detail(ex.getMessage())
                .userMessage("An unexpected error occurred. Try again later.")
                .uri(problemType.getUri())
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.BUSINESS_ERROR;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PlaylistException.class)
    public ResponseEntity<Object> handleBusinessException(PlaylistException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.PLAYLIST_ERROR;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.USER_ERROR;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<Object> handleImageException(ImageException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.IMAGE_ERROR;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Object> handleImageException(SecurityException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.SECURITY_ERROR;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(MusicException.class)
    public ResponseEntity<Object> handleMusicException(MusicException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.MUSIC_ERROR;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private Problem createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .uri(problemType.getUri())
                .title(problemType.getTitle())
                .userMessage(detail)
                .detail(detail).build();
    }
}
