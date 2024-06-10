package com.system.library.exception;

public class LibraryCustomException extends RuntimeException {
    private final String message;

    public LibraryCustomException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

