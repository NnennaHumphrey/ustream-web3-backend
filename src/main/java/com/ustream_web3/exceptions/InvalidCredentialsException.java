package com.ustream_web3.exceptions;

public class InvalidCredentialsException extends  RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
