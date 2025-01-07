package com.ustream_web3.constants;

import lombok.Getter;

public class LikeConstants {

    public enum StatusCode {
        OK("200"),
        CREATED("201"),
        NO_CONTENT("204"),
        BAD_REQUEST("400"),
        NOT_FOUND("404"),
        INTERNAL_SERVER_ERROR("500");

        @Getter
        private final String code;

        StatusCode(String code) {
            this.code = code;
        }
    }

    // Success Messages
    public static final String LIKE_ADDED = "Like added successfully.";
    public static final String LIKE_REMOVED = "Like removed successfully.";
    public static final String LIKE_FETCHED = "Likes fetched successfully.";
    public static final String LIKE_COUNT_UPDATED = "Like count updated successfully.";

    // Error Messages
    public static final String LIKE_ERROR = "There was an error adding or removing the like.";
    public static final String ALREADY_LIKED = "You have already liked this.";
    public static final String NOT_FOUND = "Like not found.";
    public static final String INVALID_LIKE_REQUEST = "Invalid request for like operation.";
    public static final String LIKE_REMOVAL_FAILED = "Failed to remove the like.";

    // Utility method for success messages based on status codes
    public static String getMessageForStatus(StatusCode statusCode) {
        switch (statusCode) {
            case CREATED:
                return LIKE_ADDED;
            case OK:
                return LIKE_FETCHED;
            case NO_CONTENT:
                return LIKE_REMOVED;
            case BAD_REQUEST:
                return INVALID_LIKE_REQUEST;
            case NOT_FOUND:
                return NOT_FOUND;
            case INTERNAL_SERVER_ERROR:
                return "An unexpected error occurred while processing like data.";
            default:
                throw new IllegalArgumentException("Unrecognized status code");
        }
    }

    // Utility method for error messages based on error codes
    public static String getErrorMessage(String errorCode) {
        switch (errorCode) {
            case "LIKE_ERROR":
                return LIKE_ERROR;
            case "ALREADY_LIKED":
                return ALREADY_LIKED;
            case "NOT_FOUND":
                return NOT_FOUND;
            case "LIKE_REMOVAL_FAILED":
                return LIKE_REMOVAL_FAILED;
            default:
                return "An unknown error occurred while handling like data.";
        }
    }
}
