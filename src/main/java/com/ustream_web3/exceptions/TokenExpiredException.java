package com.ustream_web3.exceptions;

public class TokenExpiredException extends  RuntimeException{
    public TokenExpiredException (String message) {
        super(message);
    }
}
