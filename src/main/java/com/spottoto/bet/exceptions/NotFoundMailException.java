package com.spottoto.bet.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class NotFoundMailException extends RestException{
    @Getter
    private final HttpStatus httpStatus;

    public NotFoundMailException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
