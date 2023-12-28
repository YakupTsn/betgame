package com.spottoto.bet.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private String field;

    public RestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public RestException(String field, String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.field=field;
    }

    public RestException(String field, String message) {
        super(message);
        this.httpStatus = (HttpStatus.BAD_REQUEST);
        this.field=field;
    }

    public RestException() {
        super();
        httpStatus = (HttpStatus.BAD_REQUEST);
    }

    public RestException(String message) {
        super(message);
        httpStatus = (HttpStatus.BAD_REQUEST);
    }

}
