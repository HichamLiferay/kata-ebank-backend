package com.kata.ebank.services;

import com.kata.dataset.*;
import com.kata.ebank.dtos.AccountHistoryDTO;
import com.kata.ebank.dtos.BankAccountDTO;
import com.kata.ebank.dtos.CustomerDTO;
import com.kata.ebank.entities.AccountOperation;
import com.kata.ebank.entities.BankAccount;
import com.kata.ebank.entities.Customer;
import com.kata.ebank.exceptions.BalanceNotSufficientException;
import com.kata.ebank.exceptions.BankAccountNotFoundException;
import com.kata.ebank.exceptions.CustomerNotFoundException;
import com.kata.ebank.mappers.BankAccountMapperImpl;
import com.kata.ebank.repositories.AccountOperationRepository;
import com.kata.ebank.repositories.BankAccountRepository;
import com.kata.ebank.repositories.CustomerRepository;


import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImpUTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private AccountOperationRepository accountOperationRepository;

    @Mock
    private BankAccountMapperImpl dtoMapper;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private Customer customer;

    private List<Customer> customers;

    private CustomerDTO customerDTO;

    private List<CustomerDTO> customerDTOList;

    private BankAccount bankAccount;

    private List<BankAccount> bankAccounts;


    @BeforeEach
    public void setup() throws ParseException {

        customer = CustomerEntityDummyData.generateEntityData();
        customers = CustomerEntityDummyData.generateEntityListData();
        customerDTO = CustomerDTODummyData.generateCustomerDTOData();
        customerDTOList = CustomerDTODummyData.generateCustomerDTOList();
        bankAccount = BankAccountEntityDummyData.generateEntityData();
        bankAccounts = BankAccountEntityDummyData.generateEntitiesData();
    }

    @Test
    public void givenNotEmptyCustomerList_whenListCustomers_thenReturnCustomerDTOList(){

        //given
        given(customerRepository.findAll()).willReturn(customers);
        given(dtoMapper.fromCustomer(customer)).willReturn(customerDTO);

        //when
        List<CustomerDTO> returnedCustomerDTOs = bankAccountService.listCustomers();

        //then
        assertThat(returnedCustomerDTOs).isNotNull();
        assertThat(returnedCustomerDTOs.size()).isEqualTo(2);
    }

    @Test
    public void givenEmptyCustomerList_whenListCustomers_thenReturnEmptyCustomerDTOList(){

        //given
        given(customerRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<CustomerDTO> returnedCustomerDTOs = bankAccountService.listCustomers();

        //then
        assertThat(returnedCustomerDTOs).isEmpty();
        assertThat(returnedCustomerDTOs.size()).isEqualTo(0);
    }

    @Test
    public void givenKeyword_whenSearchCustomers_thenReturnCustomerDTOList(){

        //given
        String keyword = "";
        given(customerRepository.findByNameContainsIgnoreCase(keyword)).willReturn(customers);
        given(dtoMapper.fromCustomer(customer)).willReturn(customerDTO);

        //when
        List<CustomerDTO> returnedCustomerDTOs = bankAccountService.searchCustomer(keyword);

        //then
        assertThat(returnedCustomerDTOs).isNotNull();
        assertThat(returnedCustomerDTOs.size()).isEqualTo(2);
    }

    @Test
    public void givenKeyword_whenSearchCustomers_thenReturnEmptyCustomerDTOList(){

        //given
        String keyword = "";
        given(customerRepository.findByNameContainsIgnoreCase(keyword)).willReturn(Collections.emptyList());

        //when
        List<CustomerDTO> returnedCustomerDTOs = bankAccountService.searchCustomer(keyword);

        //then
        assertThat(returnedCustomerDTOs).isEmpty();
        assertThat(returnedCustomerDTOs.size()).isEqualTo(0);
    }

    @Test
    public void givenExistingCustomerId_whenListBankAccountByCustomerId_thenBankAccountDTOList() throws Exception{

        //given
        Long customerId = 1L;
        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));
        given(bankAccountRepository.findByCustomer(customer)).willReturn(bankAccounts);

        //when
        List<BankAccountDTO> returnedBankAccountDTOs = bankAccountService.listBankAccountByCustomerId(customerId);

        //then
        assertThat(returnedBankAccountDTOs).isNotNull();
    }

    @Test
    public void givenNotExistingCustomerId_whenListBankAccountByCustomerId_thenThrowsException(){


        //given
        Long customerId = 1L;
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        //when
        org.junit.jupiter.api.Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            bankAccountService.listBankAccountByCustomerId(customerId);
        });

        //then
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    public void givenOperationRequest_WhenDeposit_thenSuccessPersistOperation_and_updateBalance() throws Exception {
        //given
        String accountId = "ab5053b7-d5bd-431e-b008-0be53b10d1f5";
        double amount = 100;
        String description = "operation desc";
        double initialBalance  = bankAccount.getBalance();
        given(bankAccountRepository.findById(accountId)).willReturn(Optional.of(bankAccount));

        //when
        bankAccountService.deposit(accountId,amount,description);

        //then
        verify(accountOperationRepository, Mockito.times(1)).save(any(AccountOperation.class));
        verify(bankAccountRepository, Mockito.times(1)).save(bankAccount);
        assertThat(bankAccount.getBalance()).isEqualTo(initialBalance + amount);
    }

    @Test
    public void givenOperationRequestWithNonexistentAccountId_WhenDeposit_thenThrowException() throws Exception{

        //given
        String accountId = "ab5053b7-d5bd-431e-b008-0be53b10d1f5";
        double amount = 100;
        String description = "operation desc";

        given(bankAccountRepository.findById(accountId)).willReturn(Optional.empty());

        //when
        org.junit.jupiter.api.Assertions.assertThrows(BankAccountNotFoundException.class, () -> {
            bankAccountService.deposit(accountId,amount,description);
        });

        //then
        verify(accountOperationRepository,  never()).save(any(AccountOperation.class));
        verify(bankAccountRepository,  never()).save(bankAccount);
    }


    @Test
    void givenOperationRequest_WhenWithdrawal_thenSuccessPersistOperation_and_updateBalance() throws Exception {
        //given
        String accountId = "ab5053b7-d5bd-431e-b008-0be53b10d1f5";
        double amount = 100;
        String description = "operation desc";
        double initialBalance  = bankAccount.getBalance();

        given(bankAccountRepository.findById(accountId)).willReturn(Optional.of(bankAccount));

        //when
        bankAccountService.withdrawal(accountId,amount,description);

        //then
        verify(accountOperationRepository, Mockito.times(1)).save(any(AccountOperation.class));
        verify(bankAccountRepository, Mockito.times(1)).save(bankAccount);
        assertThat(bankAccount.getBalance()).isEqualTo(initialBalance - amount);
    }

    @Test
    public void givenOperationRequestWithNonexistentAccountId_WhenWithdrawal_thenThrowNotFoundException() throws Exception{
        //given
        String accountId = "ab5053b7-d5bd-431e-b008-0be53b10d1f5";
        double amount = 100;
        String description = "operation desc";

        given(bankAccountRepository.findById(accountId)).willReturn(Optional.empty());

        //when
        org.junit.jupiter.api.Assertions.assertThrows(BankAccountNotFoundException.class, () -> {
            bankAccountService.withdrawal(accountId,amount,description);
        });

        //then
        verify(accountOperationRepository,  never()).save(any(AccountOperation.class));
        verify(bankAccountRepository,  never()).save(bankAccount);
    }

    @Test
    public void givenOperationRequestWithNonexistentAccountId_WhenWithdrawal_thenThrowInsufficientException() throws Exception{
        //given
        String accountId = "ab5053b7-d5bd-431e-b008-0be53b10d1f5";
        double amount = 500;
        String description = "operation desc";

        given(bankAccountRepository.findById(accountId)).willReturn(Optional.of(bankAccount));

        //when
        org.junit.jupiter.api.Assertions.assertThrows(BalanceNotSufficientException.class, () -> {
            bankAccountService.withdrawal(accountId,amount,description);
        });

        //then
        verify(accountOperationRepository,  never()).save(any(AccountOperation.class));
        verify(bankAccountRepository,  never()).save(bankAccount);
    }


    @Test
    public void givenExistingAccountId_WhenGetBankAccountHistory_thenReturnAccountHistoryDTO() throws Exception{
        //given
        String accountId = "ab5053b7-d5bd-431e-b008-0be53b10d1f5";
        int page = 0;
        int size = 5;

        given(bankAccountRepository.findById(accountId)).willReturn(Optional.of(bankAccount));
        given(accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page,size))).willReturn(AccountOperationDummyData.generateAccountOperationDTOData());
        //when
        AccountHistoryDTO accountOperations = bankAccountService.getBankAccountHistory(accountId,page,size);

        //then
        assertThat(accountOperations).isNotNull();
        assertThat(accountOperations.getTotalPages()).isEqualTo(AccountOperationDummyData.generateAccountOperationDTOData().getTotalPages());
    }


    @Test
    public void givenNotExistingAccountId_WhenGetBankAccountHistory_thenThrowException() throws Exception {
        //given
        String accountId = "ab5053b7-d5bd-431e-b008-0be53b10d1f5";
        int page = 0;
        int size = 5;

        given(bankAccountRepository.findById(accountId)).willReturn(Optional.empty());

        //when
        org.junit.jupiter.api.Assertions.assertThrows(BankAccountNotFoundException.class, () -> {
            bankAccountService.getBankAccountHistory(accountId,page,size);
        });

        //then
        verify(accountOperationRepository,  never()).findByBankAccountIdOrderByOperationDateDesc(Mockito.anyString(),Mockito.any(Pageable.class));
    }
}