package com.kata.dataset;

import com.kata.ebank.dtos.AccountHistoryDTO;
import com.kata.ebank.dtos.AccountOperationDTO;
import com.kata.ebank.entities.AccountOperation;
import com.kata.ebank.enums.OperationType;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountHistoryDTODummyData {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private AccountHistoryDTODummyData(){}

    public static AccountHistoryDTO generateAccountHistoryDTOData() throws ParseException {

        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        Page<AccountOperation> pageOfAccountOperation = AccountOperationDummyData.generateAccountOperationDTOData();
        accountHistoryDTO.setAccountId("ab5053b7-d5bd-431e-b008-0be53b10d1f5");
        accountHistoryDTO.setBalance(100);
        accountHistoryDTO.setCurrentPage(1);
        accountHistoryDTO.setPageSize(pageOfAccountOperation.getSize());
        accountHistoryDTO.setTotalPages(pageOfAccountOperation.getTotalPages());

        accountHistoryDTO.setAccountOperationsDTO(generateAccountOperationDTOData());
        return accountHistoryDTO;
    }

    public static List<AccountOperationDTO> generateAccountOperationDTOData() throws ParseException{

        List<AccountOperationDTO> accountOperationDTOList = new ArrayList<>();

        AccountOperationDTO accountOperationDTO1 = new AccountOperationDTO();
        accountOperationDTO1.setId(1L);
        accountOperationDTO1.setOperationDate(df.parse(df.format(DateUtils.setMilliseconds(new Date(),0))));
        accountOperationDTO1.setAmount(100);
        accountOperationDTO1.setDescription("Account Operation Desc");
        accountOperationDTO1.setType(OperationType.DEPOSIT);

        AccountOperationDTO accountOperationDTO2 = new AccountOperationDTO();
        accountOperationDTO2.setId(2L);
        accountOperationDTO2.setOperationDate(df.parse(df.format(DateUtils.setMilliseconds(new Date(),0))));
        accountOperationDTO2.setAmount(50);
        accountOperationDTO2.setDescription("Account Operation Desc");
        accountOperationDTO2.setType(OperationType.WITHDRAWAL);

        accountOperationDTOList.add(accountOperationDTO1);
        accountOperationDTOList.add(accountOperationDTO2);

        return accountOperationDTOList;
    }
}