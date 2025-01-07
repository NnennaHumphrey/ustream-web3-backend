package com.ustream_web3.exceptions;

public class OtpExpiredException extends RuntimeException{
    public OtpExpiredException (String message) {
        super(message);
    }
}
