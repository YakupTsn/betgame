package com.spottoto.bet.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends RestException{
    @Getter
    private final HttpStatus httpStatus;

    public UnAuthorizedException(String message) {
        super(message);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }
}
