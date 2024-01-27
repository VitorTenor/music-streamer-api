package com.music.musicStreamer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.musicStreamer.MusicStreamerApplication;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ActiveProfiles(resolver = SpringActiveProfileResolver.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MusicStreamerApplication.class)
public class AbstractContextTest {
    @Autowired
    public Environment environment;

    @Autowired
    public ObjectMapper jsonMapper;

    public PayloadExtractor payloadExtractor;

    @Autowired
    protected MockMvc mockMvc;

    public AbstractContextTest() {
    }

    @BeforeEach
    public void beforeTest() {
        this.payloadExtractor = new PayloadExtractor(jsonMapper);
    }
}

