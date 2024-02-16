package com.music.musicStreamer.api.exceptionHandler;

import com.google.gson.Gson;
import com.music.musicStreamer.enums.ProblemType;
import com.music.musicStreamer.exception.SecurityException;
import com.music.musicStreamer.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
@RequiredArgsConstructor
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
        final var problemType = ProblemType.INVALID_FIELD;

        var list = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    final var message = objectError.getDefaultMessage();
                    var name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }
                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .toList();

        var problem = Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(problemType.title())
                .detail("One or more fields are invalid. Fill in correctly and try again.")
                .userMessage("One or more fields are invalid. Fill in correctly and try again.")
                .uri(problemType.uri())
                .objects(list)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        final var status = HttpStatus.INTERNAL_SERVER_ERROR;
        final var problemType = ProblemType.UNEXPECTED_ERROR;
        var problem = Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(problemType.title())
                .detail(ex.getMessage())
                .userMessage("An unexpected error occurred. Try again later.")
                .uri(problemType.uri())
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        final var status = HttpStatus.BAD_REQUEST;
        final var problemType = ProblemType.BUSINESS_ERROR;
        final var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PlaylistException.class)
    public ResponseEntity<Object> handlePlaylistException(PlaylistException ex, WebRequest request) {
        final var status = HttpStatus.BAD_REQUEST;
        final var problemType = ProblemType.PLAYLIST_ERROR;
        final var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException ex, WebRequest request) {
        final var status = HttpStatus.BAD_REQUEST;
        final var problemType = ProblemType.USER_ERROR;
        final var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<Object> handleImageException(ImageException ex, WebRequest request) {
        final var status = HttpStatus.BAD_REQUEST;
        final var problemType = ProblemType.IMAGE_ERROR;
        final var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Object> handleSecurityException(SecurityException ex, WebRequest request) {
        final var status = HttpStatus.UNAUTHORIZED;
        final var problemType = ProblemType.SECURITY_ERROR;
        final var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(MusicException.class)
    public ResponseEntity<Object> handleMusicException(MusicException ex, WebRequest request) {
        final var status = HttpStatus.BAD_REQUEST;
        final var problemType = ProblemType.MUSIC_ERROR;
        final var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(feign.FeignException.class)
    public ResponseEntity<Object> handleUncaught(feign.FeignException ex, WebRequest request) {
        try {
            var gson = new Gson();
            var response = gson.fromJson(ex.contentUTF8(), Object.class);
            var status = HttpStatus.valueOf(ex.status());

            return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
        } catch (Exception e ){
            final var status = HttpStatus.INTERNAL_SERVER_ERROR;
            final var problemType = ProblemType.UNEXPECTED_ERROR;

            var problem = createProblemBuilder(status, problemType, ex.getMessage());

            return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        final var status = HttpStatus.UNAUTHORIZED;
        final var problemType = ProblemType.SECURITY_ERROR;
        final var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private Problem createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .uri(problemType.uri())
                .title(problemType.title())
                .userMessage(detail)
                .detail(detail)
                .build();
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
