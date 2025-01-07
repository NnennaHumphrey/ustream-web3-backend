package com.ustream_web3.exceptions;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException (String message) {
        super(message);
    }
}
