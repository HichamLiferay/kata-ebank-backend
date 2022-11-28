package com.kata.ebank.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.kata.ebank.enums.AccountStatus;
import lombok.Data;

import java.util.Date;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccountDTO {

    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
}
