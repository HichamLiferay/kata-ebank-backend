package com.kata.ebank.exceptions;

public class CustomerNotFoundException extends ApiRequestException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
