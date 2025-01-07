package com.ustream_web3.constants;

import lombok.Data;
import lombok.Getter;

@Data
public class UserConstants {

    private UserConstants() {
    }

    public enum StatusCode {
        CREATED("201"),
        OK("200"),
        NO_CONTENT("204"),
        INTERNAL_SERVER_ERROR("500"),
        UNAUTHORIZED("401"),
        FORBIDDEN("403"),
        NOT_FOUND("404"),
        BAD_REQUEST("400"),
        CONFLICT("409"),
        UNPROCESSABLE_ENTITY("422");

        @Getter
        private final String code;



        StatusCode(String code) {
            this.code = code;
        }
        public String getCode() {
            return code;
        }
    }

    // Success messages
    public static final String USER_CREATED = "User successfully created.";
    public static final String USER_LOGGED_IN = "Login successful. Welcome!";
    public static final String USER_LOGGED_OUT= "Successfully Logged Out!";
    public static final String USER_DELETED = "User has been successfully deleted.";
    public static final String USER_UPDATED = "User details updated successfully.";
    public static final String USER_USERNAME = "User username updated successfully.";
    public static final String USER_EMAIL = "User email updated successfully.";
    public static final String USER_PASSWORD = "User password updated successfully.";
    public static final String USER_PHOTO = "User photo uploaded successfully.";


    // Error messages
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String USERNAME_TAKEN = "Username is already taken";
    public static final String EMAIL_ALREADY_REGISTERED = "The email address is already registered.";
    public static final String INVALID_CREDENTIALS = "Invalid username or password. Please try again.";
    public static final String INCORRECT_PASSWORD ="Incorrect password, please input your correct password!";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An unexpected error occurred. Please try again or contact support.";
    public static final String INVALID_TOKEN = "The token provided is invalid or has expired.";
    public static final String TOKEN_EXPIRED = "Your session has expired. Please log in again.";
    public static final String UNAUTHORIZED_ACCESS = "You are not authorized to perform this action.";
    public static final String PASSWORD_RESET_FAILED = "Password reset failed. Please try again.";
    public static final String OTP_VERIFIED = "OTP verification Successfully!";
    public static final String OTP_NOT_VERIFIED = "OTP verification failed. Please try again.";
    public static final String OTP_RESENT ="OTP has been resent successfully";
    public static final String OTP_RESENT_ERROR = "Error in resending OTP, Please try again!";

    // Status codes for error handling
    public static final String USER_ALREADY_EXISTS = "The user already exists.";
    public static final String USER_ALREADY_VERIFIED = "The user is already verified";
    public static final String INVALID_REQUEST = "The request is invalid.";
    public static final String RESOURCE_NOT_FOUND = "The requested resource was not found.";
    public static final String CONFLICT_ERROR = "There was a conflict with the request.";

    // Method to retrieve success messages based on status code
    public static String getMessageForStatus(StatusCode statusCode) {
        switch (statusCode) {
            case CREATED:
                return USER_CREATED;
            case OK:
                return USER_LOGGED_IN;
            case NO_CONTENT:
                return USER_DELETED;
            case INTERNAL_SERVER_ERROR:
                return INTERNAL_SERVER_ERROR_MSG;
            case UNAUTHORIZED:
                return UNAUTHORIZED_ACCESS;
            case FORBIDDEN:
                return "You don't have permission to perform this action.";
            case NOT_FOUND:
                return RESOURCE_NOT_FOUND;
            case BAD_REQUEST:
                return INVALID_REQUEST;
            case CONFLICT:
                return USER_ALREADY_EXISTS;
            default:
                throw new IllegalArgumentException("Unrecognized status code");
        }
    }

    // Method to retrieve error messages based on error code
    public static String getErrorMessage(String errorCode) {
        switch (errorCode) {
            case "USER_NOT_FOUND":
                return USER_NOT_FOUND;
            case "EMAIL_ALREADY_REGISTERED":
                return EMAIL_ALREADY_REGISTERED;
            case "INVALID_CREDENTIALS":
                return INVALID_CREDENTIALS;
            case "INVALID_TOKEN":
                return INVALID_TOKEN;
            case "TOKEN_EXPIRED":
                return TOKEN_EXPIRED;
            case "PASSWORD_RESET_FAILED":
                return PASSWORD_RESET_FAILED;
            case "OTP_NOT_VERIFIED":
                return OTP_NOT_VERIFIED;
            case "USER_ALREADY_EXISTS":
                return USER_ALREADY_EXISTS;
            default:
                return "An unknown error occurred.";
        }
    }
}
