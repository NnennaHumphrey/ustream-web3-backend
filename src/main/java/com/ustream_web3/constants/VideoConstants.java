package com.ustream_web3.constants;

import lombok.Getter;

public class VideoConstants {

    private VideoConstants() {

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
    }

    // Success messages
    public static final String VIDEO_UPLOADED = "Video uploaded successfully.";
    public static final String VIDEO_DELETED = "Video has been successfully deleted.";
    public static final String VIDEO_UPDATED = "Video details updated successfully.";
    public static final String VIDEO_WATCHED = "Video is now available to watch.";
    public static final String VIDEO_DOWNLOADED = "Video downloaded successfully.";
    public static final String VIDEO_RETRIEVED = "Video retrieved successfully.";

    // Error messages
    public static final String VIDEO_NOT_FOUND = "Video not found.";
    public static final String VIDEO_UPLOAD_FAILED = "Failed to upload video. Please try again.";
    public static final String VIDEO_UPDATE_FAILED = "Failed to update video details.";
    public static final String VIDEO_DOWNLOAD_FAILED = "Failed to download video.";
    public static final String VIDEO_DELETE_FAILED = "Failed to delete video.";
    public static final String VIDEO_WATCH_FAILED = "Failed to retrieve video for watching.";
    public static final String UNAUTHORIZED_ACCESS = "You are not authorized to perform this action.";

    // Status codes for error handling
    public static final String INVALID_VIDEO_REQUEST = "Invalid video request.";
    public static final String VIDEO_ALREADY_EXISTS = "The video already exists.";
    public static final String VIDEO_CONFLICT = "Video conflicts with existing data.";
    public static final String RESOURCE_NOT_FOUND = "The requested video resource was not found.";

    // Method to retrieve success messages based on status code
    public static String getMessageForStatus(StatusCode statusCode) {
        switch (statusCode) {
            case CREATED:
                return VIDEO_UPLOADED;
            case OK:
                return VIDEO_RETRIEVED;
            case NO_CONTENT:
                return VIDEO_DELETED;
            case INTERNAL_SERVER_ERROR:
                return "An unexpected error occurred. Please try again or contact support.";
            case UNAUTHORIZED:
                return UNAUTHORIZED_ACCESS;
            case FORBIDDEN:
                return "You don't have permission to perform this action.";
            case NOT_FOUND:
                return VIDEO_NOT_FOUND;
            case BAD_REQUEST:
                return VIDEO_UPLOAD_FAILED;
            case CONFLICT:
                return VIDEO_ALREADY_EXISTS;
            case UNPROCESSABLE_ENTITY:
                return "Video request cannot be processed due to semantic errors.";
            default:
                throw new IllegalArgumentException("Unrecognized status code");
        }
    }

    // Method to retrieve error messages based on error code
    public static String getErrorMessage(String errorCode) {
        switch (errorCode) {
            case "VIDEO_NOT_FOUND":
                return VIDEO_NOT_FOUND;
            case "VIDEO_UPLOAD_FAILED":
                return VIDEO_UPLOAD_FAILED;
            case "VIDEO_UPDATE_FAILED":
                return VIDEO_UPDATE_FAILED;
            case "VIDEO_DOWNLOAD_FAILED":
                return VIDEO_DOWNLOAD_FAILED;
            case "VIDEO_DELETE_FAILED":
                return VIDEO_DELETE_FAILED;
            case "VIDEO_WATCH_FAILED":
                return VIDEO_WATCH_FAILED;
            case "VIDEO_CONFLICT":
                return VIDEO_CONFLICT;
            case "INVALID_VIDEO_REQUEST":
                return INVALID_VIDEO_REQUEST;
            case "RESOURCE_NOT_FOUND":
                return RESOURCE_NOT_FOUND;
            default:
                return "An unknown error occurred.";
        }
    }
}
