package com.spottoto.bet.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ForbiddenUserException extends RestException{
    @Getter
    private final HttpStatus httpStatus;

    public ForbiddenUserException(String message) {
        super(message);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }
}
