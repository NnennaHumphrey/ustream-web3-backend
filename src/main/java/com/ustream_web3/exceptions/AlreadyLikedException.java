package com.ustream_web3.exceptions;

public class AlreadyLikedException extends RuntimeException{
    public AlreadyLikedException(String message) {
        super(message);
    }

    public AlreadyLikedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyLikedException(Throwable cause) {
        super(cause);
    }

    public AlreadyLikedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AlreadyLikedException() {
    }
}
