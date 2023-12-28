package com.spottoto.bet.exceptions.handler;


import com.spottoto.bet.exceptions.RestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ErrorResponse> errorResponseList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().stream().forEach((error)->{
            ErrorResponse errorResponse= new ErrorResponse();
            errorResponse.setExceptionClass(ex.getNestedPath());
            errorResponse.setField(ex.getFieldError().getField());
            errorResponse.setMessage(error.getDefaultMessage());
            errorResponseList.add(errorResponse);

        });
        return new ResponseEntity<>(errorResponseList, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RestException.class)
    public ResponseEntity<Object> baseException(RestException e) {
        List<ErrorResponse> errorResponseList = new ArrayList<>();
        ErrorResponse errorResponse= new ErrorResponse();
        errorResponse.setExceptionClass(e.getClass().getSimpleName());
        errorResponse.setField(e.getField());
        errorResponse.setMessage(e.getMessage());
        errorResponseList.add(errorResponse);
        return new ResponseEntity<>(errorResponseList, e.getHttpStatus());
    }



}
