package com.laioffer.donationcollector.exception;

public class NGONotExistException extends RuntimeException {
    public NGONotExistException(String message) {
        super(message);
    }
}