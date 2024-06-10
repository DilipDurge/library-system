package com.system.library.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;

    public ErrorDetails(int statusCode, String message, String details) {
        super();
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }
}

