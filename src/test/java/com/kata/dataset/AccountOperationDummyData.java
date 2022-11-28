package com.kata.dataset;

import com.kata.ebank.dtos.AccountOperationDTO;
import com.kata.ebank.entities.AccountOperation;
import com.kata.ebank.enums.OperationType;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountOperationDummyData {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private AccountOperationDummyData(){}

    public static Page<AccountOperation> generateAccountOperationDTOData() throws ParseException {

        List<AccountOperation> accountOperationList = new ArrayList<>();

        AccountOperation accountOperation1 = new AccountOperation();
        accountOperation1.setId(1L);
        accountOperation1.setOperationDate(df.parse(df.format(DateUtils.setMilliseconds(new Date(),0))));
        accountOperation1.setAmount(100);
        accountOperation1.setDescription("Account Operation Desc");
        accountOperation1.setType(OperationType.DEPOSIT);

        AccountOperation accountOperation2 = new AccountOperation();
        accountOperation2.setId(2L);
        accountOperation2.setOperationDate(df.parse(df.format(DateUtils.setMilliseconds(new Date(),0))));
        accountOperation2.setAmount(50);
        accountOperation2.setDescription("Account Operation Desc");
        accountOperation2.setType(OperationType.WITHDRAWAL);

        accountOperationList.add(accountOperation1);
        accountOperationList.add(accountOperation2);

        Pageable firstPageWithTwoElements = PageRequest.of(1, 5);
        Page<AccountOperation> pageOfAccountOperationDTO = new PageImpl<>(accountOperationList, firstPageWithTwoElements, 5);
        return pageOfAccountOperationDTO;
    }
}
