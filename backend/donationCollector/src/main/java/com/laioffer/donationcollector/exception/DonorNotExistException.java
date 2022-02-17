package com.laioffer.donationcollector.exception;

public class DonorNotExistException extends RuntimeException {
    public DonorNotExistException(String message) {
        super(message);
    }
}