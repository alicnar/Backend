package com.spring.gamebackend.Exceptions;

public class WrongPasswordException extends RuntimeException{
    //Exception is throw when user tried to log in wrong password.
    public WrongPasswordException(String message){
        super(message);
    }
}
