package com.music.musicStreamer.api.exceptionHandler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Schema(name = "Problem")
public class Problem {
    @Schema(example = "400")
    private String title;
    @Schema(example = "Invalid parameter")
    private String detail;
    @Schema(example = "Invalid parameter")
    private Integer status;
    @Schema(example = "/invalid-parameter")
    private String userMessage;
    @Schema(example = "2022-07-15T11:21:50")
    private LocalDateTime timestamp;
    @Schema(description = "List of objects or fields that generated the error")
    private String uri;
}
