package com.restdemo.exceptionHandling;

public class ExceptionHandling extends RuntimeException {

    public ExceptionHandling(final String exception){
        super(exception);
    }
}