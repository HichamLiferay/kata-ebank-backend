package com.kata.ebank.exceptions;

public class BankAccountNotFoundException extends ApiRequestException {
    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
