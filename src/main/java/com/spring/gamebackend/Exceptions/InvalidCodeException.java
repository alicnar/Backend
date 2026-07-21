package com.spring.gamebackend.Exceptions;

public class InvalidCodeException extends RuntimeException{
    //Exception is throw when user send wrong verification code.
    public InvalidCodeException(String message){
        super(message);
    }
}
