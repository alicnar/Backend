package com.spring.gamebackend.Exceptions;

public class RegisteredMailException extends RuntimeException{
    //Exception is throw when there is a user withs same userName in app.
    public RegisteredMailException(String message){
        super(message);
    }
}
