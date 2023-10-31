package com.example.emapp.ExceptionHandle;

import java.io.Serializable;

public class CustomApiErrorResponse implements Serializable {
    private int statusCode;
    private String message;
    private String detailedMessage;

    public CustomApiErrorResponse(int code, String msg, String details){
        this.statusCode = code;
        this.message = msg;
        this.detailedMessage = details;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }
}
