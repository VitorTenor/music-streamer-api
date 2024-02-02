package com.music.musicStreamer.core.util.factory;

import org.slf4j.LoggerFactory;

public final class LogFactory {
    public static void info(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz.getName()).info(message);
    }
}
