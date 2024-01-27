package com.music.musicStreamer.util;

import org.springframework.test.context.ActiveProfilesResolver;

public class SpringActiveProfileResolver implements ActiveProfilesResolver {
    private final String[] defaultActiveProfile = {"test"};

    @Override
    public String[] resolve(Class<?> aClass) {
        return defaultActiveProfile;
    }
}
