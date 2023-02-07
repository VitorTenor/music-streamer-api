package com.music.musicStreamer.api.exceptionHandler;

import com.music.musicStreamer.api.exceptionHandler.handler.NotFoundException;
import com.music.musicStreamer.api.exceptionHandler.handler.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class ExceptionHandlerTypes extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionHandlerResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionHandlerResponse exceptionHandlerResponse = new ExceptionHandlerResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionHandlerResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionHandlerResponse> handleNotFound(Exception ex, WebRequest request) {
        ExceptionHandlerResponse exceptionHandlerResponse = new ExceptionHandlerResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionHandlerResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ExceptionHandlerResponse> handleUnauthorized(Exception ex, WebRequest request) {
        ExceptionHandlerResponse exceptionHandlerResponse = new ExceptionHandlerResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionHandlerResponse, HttpStatus.NOT_FOUND);
    }
}
