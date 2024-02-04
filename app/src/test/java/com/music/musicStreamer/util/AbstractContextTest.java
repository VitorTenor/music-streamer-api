package com.music.musicStreamer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.musicStreamer.MusicStreamerApplication;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
    public void beforeTest(WebApplicationContext context) {
        MockitoAnnotations.openMocks(this);
        this.payloadExtractor = new PayloadExtractor(jsonMapper);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }


    /*
     * Messages
     */
    protected final String INVALID_FIELD_TITLE = "Invalid field";
    protected final String REQUIRED_FIELD = "%s is required";
    protected final String INVALID_FIELD = "%s is invalid";
    protected final String ONE_OR_MORE_FIELDS_ARE_INVALID = "One or more fields are invalid. Fill in correctly and try again.";
}

