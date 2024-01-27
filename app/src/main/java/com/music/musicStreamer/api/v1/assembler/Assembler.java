package com.music.musicStreamer.api.v1.assembler;

/**
 * Interface to facilitate the conversion of {@code entities/mappers} to {@code DTOs/Responses}.
 */
public interface Assembler<E, R> {
    R toResponse(E entity);
}
