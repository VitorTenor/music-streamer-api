package com.music.musicStreamer.core.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class GenerateName {

    private static final Logger LOGGER = Logger.getLogger(GenerateName.class.getName());

    public static String randomName() {
        LOGGER.info("[GenerateName] Generate random name");

        Random random = new Random();
        Instant instant = Instant.now();
        String generatedName = (instant.toString() + "_" + random.nextLong())
                .replace(":", "_").replace(".", "_");

        LOGGER.info("[GenerateName] Generated name => " + generatedName);

        return generatedName;
    }
}
