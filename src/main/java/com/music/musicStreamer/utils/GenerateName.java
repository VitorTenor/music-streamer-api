package com.music.musicStreamer.utils;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class GenerateName {
    public String randomName() {
        Instant instant = Instant.now();
        long randomValue = new Random().nextLong();
        String generatedName = instant.toString() + "_" + Long.toString(randomValue);
        String newName = generatedName.replace(":", "_").replace(".", "_");
        return newName;
    }
}
