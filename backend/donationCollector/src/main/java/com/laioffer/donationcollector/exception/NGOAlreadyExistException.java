package com.laioffer.donationcollector.exception;

public class NGOAlreadyExistException extends RuntimeException {
    public NGOAlreadyExistException(String message) {
        super(message);
    }
}