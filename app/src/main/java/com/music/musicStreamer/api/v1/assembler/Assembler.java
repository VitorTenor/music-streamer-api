package com.music.musicStreamer.api.v1.assembler;

import java.util.List;

/**
 * Interface to facilitate the conversion of {@code entities} to {@code DTOs/Output}.
 */
public interface Assembler<E, O> {
    O toOutput(E entity);

    default List<O> toOutputList(List<E> entities) {
        return entities.stream().map(this::toOutput).toList();
    }
}
