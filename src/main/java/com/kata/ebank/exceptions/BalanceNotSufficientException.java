package com.kata.ebank.exceptions;

public class BalanceNotSufficientException extends ApiRequestException {
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
