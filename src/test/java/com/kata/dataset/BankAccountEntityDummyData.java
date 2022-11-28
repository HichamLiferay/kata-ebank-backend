package com.kata.dataset;

import com.kata.ebank.entities.BankAccount;
import com.kata.ebank.enums.AccountStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankAccountEntityDummyData {

    private BankAccountEntityDummyData(){}

    public static BankAccount generateEntityData(){

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId("ab5053b7-d5bd-431e-b008-0be53b10d1f5");
        bankAccount.setCustomer(CustomerEntityDummyData.generateEntityData());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(100);
        bankAccount.setStatus(AccountStatus.CREATED);

        return bankAccount;
    }

    public static List<BankAccount> generateEntitiesData(){

        List<BankAccount> bankAccounts = new ArrayList<>();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId("cb5053b7-d5b6-4312-b009-0be53b10d1f2");
        bankAccount.setCustomer(CustomerEntityDummyData.generateEntityData());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(0);
        bankAccount.setStatus(AccountStatus.CREATED);

        bankAccounts.add(generateEntityData());
        bankAccounts.add(bankAccount);

        return bankAccounts;
    }
}
