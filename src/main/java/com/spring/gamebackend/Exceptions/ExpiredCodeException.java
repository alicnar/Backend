package com.spring.gamebackend.Exceptions;

public class ExpiredCodeException extends RuntimeException{
    //Exception is throw when user send verification code after 3 minutes.

    public ExpiredCodeException(String message){
        super(message);
    }
}
