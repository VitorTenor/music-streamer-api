package com.music.musicStreamer;

import com.music.musicStreamer.api.v1.clients.*;
import com.music.musicStreamer.api.v1.controllers.TestImageController;
import com.music.musicStreamer.api.v1.controllers.TestMusicController;
import com.music.musicStreamer.api.v1.controllers.TestPlaylistController;
import com.music.musicStreamer.api.v1.controllers.TestUserController;
import com.music.musicStreamer.api.v1.models.dtos.TestAddMusicDTO;
import com.music.musicStreamer.api.v1.models.dtos.TestAddMusicPlaylistDTO;
import com.music.musicStreamer.api.v1.models.dtos.TestCreatePlaylistDTO;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        TestImageClient.class,
        TestMusicClient.class,
        TestPlaylistClient.class,
        TestPlaylistMusicClient.class,
        TestUserClient.class,
        TestImageController.class,
        TestMusicController.class,
        TestPlaylistController.class,
        TestUserController.class,
        TestAddMusicDTO.class,
        TestAddMusicPlaylistDTO.class,
        TestCreatePlaylistDTO.class

})
public class ApplicationTestSuite {
}
