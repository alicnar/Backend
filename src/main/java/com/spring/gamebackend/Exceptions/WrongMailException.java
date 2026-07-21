package com.spring.gamebackend.Exceptions;

public class WrongMailException extends RuntimeException{
    //Exception is throw when user tried to log in with wrong mail.

    public WrongMailException(String message){
        super(message);
    }
}
