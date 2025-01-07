package com.ustream_web3.exceptions;

public class UnauthorizedOperationException extends RuntimeException{
    public UnauthorizedOperationException(String message) {
        super(message);
    }
}
