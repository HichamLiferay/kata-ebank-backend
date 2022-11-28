package com.kata.ebank.mappers;

import com.kata.ebank.dtos.AccountOperationDTO;
import com.kata.ebank.dtos.BankAccountDTO;
import com.kata.ebank.dtos.CustomerDTO;
import com.kata.ebank.entities.AccountOperation;
import com.kata.ebank.entities.BankAccount;
import com.kata.ebank.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }

    public BankAccountDTO fromBankAccount(BankAccount bankAccount){
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        BeanUtils.copyProperties(bankAccount,bankAccountDTO);
        return bankAccountDTO;
    }

    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }
}
