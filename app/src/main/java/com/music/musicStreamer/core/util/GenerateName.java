package com.music.musicStreamer.core.util;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class GenerateName {

    private Random random = new Random();

    public String randomName() {
        Instant instant = Instant.now();
        String generatedName = instant.toString() + "_" + Long.toString(random.nextLong());
        return generatedName.replace(":", "_").replace(".", "_");
    }
}
