package com.kata.ebank.repositories;

import com.kata.ebank.entities.BankAccount;
import com.kata.ebank.entities.Customer;
import com.kata.ebank.enums.AccountStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankAccountRepositoryUTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void find_no_bank_account_if_repo_is_empty(){

        Iterable<BankAccount> bankAccounts = bankAccountRepository.findAll();
        assertThat(bankAccounts).isEmpty();
    }

    @Test
    public void save_a_bank_account(){

        BankAccount expectedBankAccount = new BankAccount("ab5053b7-d5bd-431e-b008-0be53b10d1f5",100,new Date(), AccountStatus.CREATED,null,null);

        BankAccount returnedBankAccount = bankAccountRepository.save(expectedBankAccount);

        assertThat(returnedBankAccount).isNotNull();
        assertThat(returnedBankAccount).isEqualTo(expectedBankAccount);
    }

    @Test
    public void find_bank_account_by_id(){

        BankAccount bankAccount1 = new BankAccount("ab5053b7-d5bd-431e-b008-0be53b10d1f5",100,new Date(), AccountStatus.CREATED,null,null);
        bankAccountRepository.save(bankAccount1);

        BankAccount bankAccount2 = new BankAccount("ab5053b7-d5bd-431e-b008-0be53b10d1f4",100,new Date(), AccountStatus.CREATED,null,null);
        bankAccountRepository.save(bankAccount2);

        BankAccount returnedBankAccount = bankAccountRepository.findById(bankAccount2.getId()).get();

        assertThat(returnedBankAccount).isNotNull();
        assertThat(returnedBankAccount).isEqualTo(bankAccount2);
    }


    @Test
    public void find_bank_account_by_customer_id(){

        Customer customer1 = new Customer(1L,"Test 1","Test1@mail",null);
        customerRepository.save(customer1);

        BankAccount bankAccount = new BankAccount("ab5053b7-d5bd-431e-b008-0be53b10d1f5",100,new Date(), AccountStatus.CREATED,customer1,null);

        BankAccount returnedBankAccount = bankAccountRepository.save(bankAccount);

        assertThat(returnedBankAccount).isNotNull();
        assertThat(returnedBankAccount.getCustomer().getId()).isEqualTo(customer1.getId());

    }
}
