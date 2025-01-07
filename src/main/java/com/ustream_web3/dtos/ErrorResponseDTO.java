package com.ustream_web3.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {
    private String apiPath;

    private HttpStatus errorCode;

    private  String errorMessage;

    public ErrorResponseDTO(String apiPath, HttpStatus errorCode, String errorMessage, LocalDateTime errorTime) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorTime = errorTime;
    }

    private LocalDateTime errorTime;
}
