package com.music.musicStreamer.api.exceptionHandler;

import com.music.musicStreamer.api.exceptionHandler.Problem;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Problem Test")
public class TestProblem {

    @Test
    @Order(1)
    @DisplayName("001 - Problem constructor test")
    public void testProblemBuilder() {
        LocalDateTime timestamp = LocalDateTime.now();
        String title = "Test Title";
        String detail = "Test Detail";
        Integer status = 400;
        String userMessage = "Test User Message";
        String uri = "/test-uri";

        Problem problem = Problem.builder()
                .timestamp(timestamp)
                .title(title)
                .detail(detail)
                .status(status)
                .userMessage(userMessage)
                .uri(uri)
                .build();

        Problem problem2 = new Problem(null, null, null, null, null, null);
        problem2.setTimestamp(timestamp);
        problem2.setTitle(title);
        problem2.setDetail(detail);
        problem2.setStatus(status);
        problem2.setUserMessage(userMessage);
        problem2.setUri(uri);

        assertEquals(timestamp, problem.getTimestamp());
        assertEquals(title, problem.getTitle());
        assertEquals(detail, problem.getDetail());
        assertEquals(status, problem.getStatus());
        assertEquals(userMessage, problem.getUserMessage());
        assertEquals(uri, problem.getUri());

        assertEquals(timestamp, problem2.getTimestamp());
        assertEquals(title, problem2.getTitle());
        assertEquals(detail, problem2.getDetail());
        assertEquals(status, problem2.getStatus());
        assertEquals(userMessage, problem2.getUserMessage());
        assertEquals(uri, problem2.getUri());
    }
}
