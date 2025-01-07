package com.ustream_web3.constants;

import lombok.Getter;

public class PasswordConstants {

    private PasswordConstants() {
    }

    // Enum for Status Codes
    public enum StatusCode {
        OK("200"),
        CREATED("201"),
        NO_CONTENT("204"),
        BAD_REQUEST("400"),
        UNAUTHORIZED("401"),
        FORBIDDEN("403"),
        NOT_FOUND("404"),
        INTERNAL_SERVER_ERROR("500");

        @Getter
        private final String code;

        StatusCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }


    }

    // Success Messages
    public static final String PASSWORD_RESET_REQUEST = "Password reset request successful. Please check your email.";
    public static final String PASSWORD_RESET_SUCCESS = "Your password has been successfully reset.";
    public static final String PASSWORD_CHANGED = "Your password has been changed successfully.";
    public static final String PASSWORD_STRENGTH_VALID = "The password meets the strength requirements.";

    // Error Messages
    public static final String PASSWORD_RESET_FAILED = "Password reset failed. Please try again.";
    public static final String PASSWORD_TOO_WEAK = "The password is too weak. Please choose a stronger password.";
    public static final String PASSWORD_MISMATCH = "The passwords do not match. Please try again.";
    public static final String PASSWORD_TOKEN_EXPIRED = "The password reset token has expired. Please request a new one.";
    public static final String PASSWORD_TOKEN_INVALID = "The password reset token is invalid or already used.";
    public static final String PASSWORD_CHANGE_NOT_ALLOWED = "Password change is not allowed for this user.";

    // Utility Methods

    // Success message based on status code
    public static String getMessageForStatus(StatusCode statusCode) {
        switch (statusCode) {
            case OK:
                return PASSWORD_CHANGED;
            case CREATED:
                return PASSWORD_RESET_REQUEST;
            case NO_CONTENT:
                return PASSWORD_RESET_SUCCESS;
            case BAD_REQUEST:
                return PASSWORD_TOO_WEAK;
            case UNAUTHORIZED:
                return PASSWORD_TOKEN_INVALID;
            default:
                return "An unexpected error occurred with the password operation.";
        }
    }

    // Error message based on error code
    public static String getErrorMessage(String errorCode) {
        switch (errorCode) {
            case "PASSWORD_RESET_FAILED":
                return PASSWORD_RESET_FAILED;
            case "PASSWORD_TOO_WEAK":
                return PASSWORD_TOO_WEAK;
            case "PASSWORD_MISMATCH":
                return PASSWORD_MISMATCH;
            case "PASSWORD_TOKEN_EXPIRED":
                return PASSWORD_TOKEN_EXPIRED;
            case "PASSWORD_TOKEN_INVALID":
                return PASSWORD_TOKEN_INVALID;
            case "PASSWORD_CHANGE_NOT_ALLOWED":
                return PASSWORD_CHANGE_NOT_ALLOWED;
            default:
                return "An unknown error occurred while processing the password request.";
        }
    }
}
