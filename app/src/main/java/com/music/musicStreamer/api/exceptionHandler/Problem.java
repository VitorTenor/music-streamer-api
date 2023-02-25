package com.music.musicStreamer.api.exceptionHandler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class Problem {
    private String title;
    private String detail;
    private Integer status;
    private String userMessage;
    private LocalDateTime timestamp;
    private String uri;
}
