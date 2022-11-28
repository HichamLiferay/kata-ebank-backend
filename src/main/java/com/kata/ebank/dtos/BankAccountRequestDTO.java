package com.kata.ebank.dtos;

import lombok.Data;

@Data
public class BankAccountRequestDTO {

    private Long customerId;
    private double amount;
}
