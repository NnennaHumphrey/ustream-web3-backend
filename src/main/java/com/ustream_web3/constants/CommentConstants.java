package com.ustream_web3.constants;

import lombok.Getter;

public class CommentConstants {

    private CommentConstants() {
    }

    public enum StatusCode {
        CREATED("201"),
        OK("200"),
        NO_CONTENT("204"),
        INTERNAL_SERVER_ERROR("500"),
        BAD_REQUEST("400");

        @Getter
        private final String code;

        StatusCode(String code) {
            this.code = code;
        }
    }

    // Success Messages
    public static final String COMMENT_ADDED = "Comment successfully added.";
    public static final String COMMENT_REPLIED = "Reply to comment successfully added.";
    public static final String COMMENTS_RETRIEVED = "Comments retrieved successfully.";
    public static final String COMMENT_DELETED = "Comment successfully deleted.";

    // Error Messages
    public static final String COMMENT_NOT_FOUND = "Comment not found.";
    public static final String COMMENT_CREATION_FAILED = "Failed to create comment. Please try again.";
    public static final String COMMENT_DELETION_FAILED = "Failed to delete comment. Please try again.";
    public static final String COMMENT_REPLY_FAILED = "Failed to reply to comment. Please try again.";

    // Utility method for retrieving status messages based on status code
    public static String getMessageForStatus(StatusCode statusCode) {
        switch (statusCode) {
            case CREATED:
                return COMMENT_ADDED;
            case OK:
                return COMMENTS_RETRIEVED;
            case NO_CONTENT:
                return COMMENT_DELETED;
            case INTERNAL_SERVER_ERROR:
                return "An unexpected error occurred while processing your request.";
            case BAD_REQUEST:
                return "Invalid request. Please check your input.";
            default:
                throw new IllegalArgumentException("Unrecognized status code");
        }
    }

    // Utility method to handle errors based on error codes
    public static String getErrorMessage(String errorCode) {
        switch (errorCode) {
            case "COMMENT_NOT_FOUND":
                return COMMENT_NOT_FOUND;
            case "COMMENT_CREATION_FAILED":
                return COMMENT_CREATION_FAILED;
            case "COMMENT_DELETION_FAILED":
                return COMMENT_DELETION_FAILED;
            case "COMMENT_REPLY_FAILED":
                return COMMENT_REPLY_FAILED;
            default:
                return "An unknown error occurred while handling your request.";
        }
    }
}
