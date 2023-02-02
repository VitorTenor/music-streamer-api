package com.music.musicStreamer;

import com.music.musicStreamer.api.v1.controllers.ImageControllerTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(JUnitPlatform.class)
@SelectPackages({
        "com.music.musicStreamer.api.v1.controllers",
})
@SuiteDisplayName("MusicStreamerApplicationTests")
@Suite.SuiteClasses({
        ImageControllerTest.class,
})
public class MusicStreamerApplicationTests {
}
