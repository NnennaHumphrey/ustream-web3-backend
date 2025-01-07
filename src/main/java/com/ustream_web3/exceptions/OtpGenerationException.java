package com.ustream_web3.exceptions;

public class OtpGenerationException extends RuntimeException{
    public OtpGenerationException(String message) {
        super(message);
    }

    public OtpGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
