package com.restdemo.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionHandling extends RuntimeException {

    public ExceptionHandling(String exception){
        super(exception);
    }
}