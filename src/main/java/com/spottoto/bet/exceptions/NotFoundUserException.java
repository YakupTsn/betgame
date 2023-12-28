package com.spottoto.bet.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class NotFoundUserException extends RestException{
    @Getter
    private final HttpStatus httpStatus;

    public NotFoundUserException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
