package com.music.musicStreamer;

import com.music.musicStreamer.api.exceptionHandler.TestApplicationExceptionHandler;
import com.music.musicStreamer.api.exceptionHandler.TestProblem;
import com.music.musicStreamer.api.v1.clients.*;
import com.music.musicStreamer.api.v1.controllers.TestImageController;
import com.music.musicStreamer.api.v1.controllers.TestMusicController;
import com.music.musicStreamer.api.v1.controllers.TestPlaylistController;
import com.music.musicStreamer.api.v1.controllers.TestUserController;
import com.music.musicStreamer.api.v1.models.*;
import com.music.musicStreamer.api.v1.models.dtos.*;
import com.music.musicStreamer.api.v1.request.TestUserRegister;
import com.music.musicStreamer.core.storage.impl.TestImageFiles;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        TestApplicationExceptionHandler.class,
        TestProblem.class,
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
        TestCreatePlaylistDTO.class,
        TestUserLogin.class,
        TestUserRegister.class,
        TestPlaylistDTO.class,
        TestImageModel.class,
        TestMusicModel.class,
        TestPlaylistModel.class,
        TestPlaylistMusicModel.class,
        TestUserModel.class,
        TestImageFiles.class,

})
public class ApplicationTestSuite {
}
