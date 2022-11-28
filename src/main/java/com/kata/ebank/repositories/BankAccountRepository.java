package com.kata.ebank.repositories;

import com.kata.ebank.entities.BankAccount;
import com.kata.ebank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    List<BankAccount> findByCustomer(Customer customer);
}
