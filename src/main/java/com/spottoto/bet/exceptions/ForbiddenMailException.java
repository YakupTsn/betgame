package com.spottoto.bet.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ForbiddenMailException extends RestException{
    @Getter
    private final HttpStatus httpStatus;

    public ForbiddenMailException(String message) {
        super(message);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }
}
