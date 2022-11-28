package com.kata.ebank.repositories;

import com.kata.ebank.entities.AccountOperation;
import com.kata.ebank.entities.BankAccount;
import com.kata.ebank.enums.AccountStatus;
import com.kata.ebank.enums.OperationType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.domain.Page;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountOperationRepositoryUTest {

    @Autowired
    private AccountOperationRepository accountOperationRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    public void find_no_bank_operation_if_repo_is_empty(){

        Iterable<AccountOperation> accountOperations = accountOperationRepository.findAll();
        assertThat(accountOperations).isEmpty();
    }

    @Test
    public void save_a_account_operation(){

        AccountOperation expectedAccountOperation = new AccountOperation(3L,new Date(),50, OperationType.DEPOSIT,null,"operation desc");

        AccountOperation returnedAccountOperation = accountOperationRepository.save(expectedAccountOperation);

        assertThat(returnedAccountOperation).isNotNull();
        assertThat(returnedAccountOperation).isEqualTo(expectedAccountOperation);

    }

    @Test
    public void find_operations_by_account_id(){

        BankAccount bankAccount = new BankAccount("ab5053b7-d5bd-431e-b008-0be53b10d1f5",100,new Date(), AccountStatus.CREATED,null,null);
        bankAccountRepository.save(bankAccount);

        AccountOperation expectedAccountOperation1 = new AccountOperation(1L,new Date(),50, OperationType.DEPOSIT,bankAccount,"operation desc");
        AccountOperation expectedAccountOperation2 = new AccountOperation(2L,new Date(),50, OperationType.WITHDRAWAL,bankAccount,"operation desc");
        accountOperationRepository.save(expectedAccountOperation1);
        accountOperationRepository.save(expectedAccountOperation2);

        Page<AccountOperation> returnedOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(bankAccount.getId(),PageRequest.of(0,5));

        assertThat(returnedOperations).isNotNull();
        assertThat(returnedOperations.getContent().size()).isEqualTo(2);
        assertThat(returnedOperations.getContent().get(0).getBankAccount().getId()).isEqualTo(bankAccount.getId());

    }


}
