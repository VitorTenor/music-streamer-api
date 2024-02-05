package com.music.musicStreamer.core.util;

import java.time.Instant;

import static org.apache.commons.lang3.RandomUtils.nextLong;

public class MainUtils {
    public static String randomName() {
        var instant = Instant.now();

        return (instant.toString() + "_" +
                nextLong()).replace(":", "_").replace(".", "_");
    }
}
