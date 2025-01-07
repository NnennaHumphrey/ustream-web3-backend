package com.ustream_web3.constants;

import lombok.Getter;

public class LeaderboardConstants {


    private LeaderboardConstants() {
    }

    // Enum for Status Codes
    public enum StatusCode {
        CREATED("201"),
        OK("200"),
        NO_CONTENT("204"),
        INTERNAL_SERVER_ERROR("500"),
        BAD_REQUEST("400"),
        NOT_FOUND("404");

        @Getter
        private final String code;

        StatusCode(String code) {
            this.code = code;
        }
    }

    // Success Messages
    public static final String LEADERBOARD_UPDATED = "Leaderboard updated successfully.";
    public static final String LEADERBOARD_FETCHED = "Leaderboard fetched successfully.";
    public static final String LEADERBOARD_ENTRY_ADDED = "New entry added to the leaderboard successfully.";
    public static final String LEADERBOARD_RESET = "Leaderboard reset successfully.";

    // Error Messages
    public static final String LEADERBOARD_NOT_FOUND = "Leaderboard not found.";
    public static final String LEADERBOARD_ERROR = "There was an error updating the leaderboard.";
    public static final String LEADERBOARD_ENTRY_FAILED = "Failed to add entry to the leaderboard.";
    public static final String INVALID_LEADERBOARD_REQUEST = "Invalid request for leaderboard operation.";
    public static final String LEADERBOARD_RESET_FAILED = "Failed to reset the leaderboard.";

    // Utility method for success messages based on status codes
    public static String getMessageForStatus(StatusCode statusCode) {
        switch (statusCode) {
            case CREATED:
                return LEADERBOARD_ENTRY_ADDED;
            case OK:
                return LEADERBOARD_FETCHED;
            case NO_CONTENT:
                return LEADERBOARD_RESET;
            case INTERNAL_SERVER_ERROR:
                return "An unexpected error occurred while processing leaderboard data.";
            case BAD_REQUEST:
                return INVALID_LEADERBOARD_REQUEST;
            case NOT_FOUND:
                return LEADERBOARD_NOT_FOUND;
            default:
                throw new IllegalArgumentException("Unrecognized status code");
        }
    }

    // Utility method for error messages based on error codes
    public static String getErrorMessage(String errorCode) {
        switch (errorCode) {
            case "LEADERBOARD_NOT_FOUND":
                return LEADERBOARD_NOT_FOUND;
            case "LEADERBOARD_ERROR":
                return LEADERBOARD_ERROR;
            case "LEADERBOARD_ENTRY_FAILED":
                return LEADERBOARD_ENTRY_FAILED;
            case "LEADERBOARD_RESET_FAILED":
                return LEADERBOARD_RESET_FAILED;
            default:
                return "An unknown error occurred while handling leaderboard data.";
        }
    }


}
