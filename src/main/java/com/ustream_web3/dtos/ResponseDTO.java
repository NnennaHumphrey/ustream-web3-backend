package com.ustream_web3.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseDTO {
    private String statusCode;

    private String statusMsg;

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public ResponseDTO(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }
}
