package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.model.output.PlaylistOutput;
import com.music.musicStreamer.util.AbstractContextTest;
import com.music.musicStreamer.util.JwtUtil;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration tests for PlaylistController")
@Sql(
        scripts = {
                "classpath:sql/delete.sql",
                "classpath:sql/insertPlaylistControllerTest.sql"
        }
)
public class TestPlaylistController extends AbstractContextTest {

    private final String PATH = "/v1/playlists";

    @Test
    @Order(1)
    @DisplayName("001 - Test create playlist - success")
    public void test001() throws Exception {
        // arrange
        var jsonRequest =
                """ 
                    {
                        "name": "My playlist"
                    }
                """;

        var request = MockMvcRequestBuilders
                .post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.generateToken())
                .content(jsonRequest);

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

        var playlist = this.payloadExtractor.as(PlaylistOutput.class);

        assertNotNull(playlist);
        assertNotNull(playlist.getId());
        assertEquals("My playlist", playlist.getName());
        assertEquals(0, playlist.getMusicIds().size());
    }

}
