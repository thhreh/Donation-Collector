package com.laioffer.donationcollector.exception;

public class DonorAlreadyExistException extends RuntimeException {
    public DonorAlreadyExistException(String message) {
        super(message);
    }
}

