package com.music.musicStreamer.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
public class InputStreamDTO {
    InputStreamResource resource;
    File file;
}
