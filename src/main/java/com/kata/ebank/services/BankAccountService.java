package com.kata.ebank.services;

import com.kata.ebank.dtos.AccountHistoryDTO;
import com.kata.ebank.dtos.BankAccountDTO;
import com.kata.ebank.dtos.CustomerDTO;

import com.kata.ebank.exceptions.BalanceNotSufficientException;
import com.kata.ebank.exceptions.BankAccountNotFoundException;
import com.kata.ebank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    List<CustomerDTO> listCustomers();
    List<CustomerDTO> searchCustomer(String keyword);
    List<BankAccountDTO> listBankAccountByCustomerId(Long customerId) throws CustomerNotFoundException;
    void deposit(String accountId,double amount,String description) throws BankAccountNotFoundException;
    void withdrawal(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    AccountHistoryDTO getBankAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

}
