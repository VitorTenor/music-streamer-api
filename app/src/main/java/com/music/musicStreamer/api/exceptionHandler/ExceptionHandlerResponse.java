package com.music.musicStreamer.api.exceptionHandler;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionHandlerResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date timestamp;
    private String message;
    private String details;
}
