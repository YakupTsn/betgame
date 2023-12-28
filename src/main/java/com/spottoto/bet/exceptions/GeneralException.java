package com.spottoto.bet.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class GeneralException extends RestException{
    @Getter
    private final HttpStatus httpStatus;

    public GeneralException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
