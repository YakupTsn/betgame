package com.spottoto.bet.exceptions;

public class UserMailExistsException extends RestException{
    public UserMailExistsException(String message){
        super(message);
    }
}
