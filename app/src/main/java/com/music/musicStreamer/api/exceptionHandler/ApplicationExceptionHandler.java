package com.music.musicStreamer.api.exceptionHandler;

import com.google.gson.Gson;
import com.music.musicStreamer.exceptions.SecurityException;
import com.music.musicStreamer.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request, BindingResult bindingResult) {
        ProblemType problemType = ProblemType.INVALID_FIELD;

        List<Problem.Object> list = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = objectError.getDefaultMessage();
                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }
                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(java.util.stream.Collectors.toList());

        var problem = Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(problemType.getTitle())
                .detail("One or more fields are invalid. Fill in correctly and try again.")
                .userMessage("One or more fields are invalid. Fill in correctly and try again.")
                .uri(problemType.getUri())
                .objects(list)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
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
    public ResponseEntity<Object> handlePlaylistException(PlaylistException ex, WebRequest request) {
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
    public ResponseEntity<Object> handleSecurityException(SecurityException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
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

    @ExceptionHandler(feign.FeignException.class)
    public ResponseEntity<Object> handleUncaught(feign.FeignException ex, WebRequest request) {
        try {
            Gson gson = new Gson();
            Object response = gson.fromJson(ex.contentUTF8(), Object.class);
            HttpStatus status = HttpStatus.valueOf(ex.status());

            return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
        } catch (Exception e ){
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            ProblemType problemType = ProblemType.UNEXPECTED_ERROR;

            Problem problem = createProblemBuilder(status, problemType, ex.getMessage());

            return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }
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

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }

        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(body, headers, status);
    }

}
