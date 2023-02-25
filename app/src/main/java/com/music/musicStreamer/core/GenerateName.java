package com.music.musicStreamer.core;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class GenerateName {
    public String randomName() {
        Instant instant = Instant.now();
        long randomValue = new Random().nextLong();
        String generatedName = instant.toString() + "_" + Long.toString(randomValue);
        return generatedName.replace(":", "_").replace(".", "_");
    }
}
