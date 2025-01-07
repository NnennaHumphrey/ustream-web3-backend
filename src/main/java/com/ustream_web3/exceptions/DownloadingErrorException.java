package com.ustream_web3.exceptions;

public class DownloadingErrorException extends RuntimeException{
    public DownloadingErrorException(String message) {
        super(message);
    }
}
