package com.kata.ebank.dtos;

import com.kata.ebank.entities.BankAccount;
import com.kata.ebank.enums.OperationType;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;

}
