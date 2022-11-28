package com.kata.dataset;

import com.kata.ebank.dtos.BankAccountDTO;
import com.kata.ebank.enums.AccountStatus;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankAccountDTODummyData {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private BankAccountDTODummyData(){}

    public static BankAccountDTO generateBankAccountDTOData() throws ParseException {

        BankAccountDTO bankAccountDTO = new BankAccountDTO();

        bankAccountDTO.setId("ab5053b7-d5bd-431e-b008-0be53b10d1f5");
        bankAccountDTO.setBalance(100);
        bankAccountDTO.setCreatedAt(df.parse(df.format(DateUtils.setMilliseconds(new Date(),0))));

        bankAccountDTO.setStatus(AccountStatus.CREATED);
        //bankAccountDTO.setCustomerDTO(CustomerDTODummyData.generateCustomerDTOData());

        return bankAccountDTO;
    }

    public static BankAccountDTO generateBankAccountDTOWithoutCustomer() throws ParseException{

        BankAccountDTO bankAccountDTO = new BankAccountDTO();

        bankAccountDTO.setId("ab5053b7-d5bd-431e-b008-0be53b10d1f5");
        bankAccountDTO.setBalance(100);
        bankAccountDTO.setCreatedAt(df.parse(df.format(DateUtils.setMilliseconds(new Date(),0))));

        bankAccountDTO.setStatus(AccountStatus.CREATED);

        return bankAccountDTO;
    }

    public static List<BankAccountDTO> generateBankAccountDTOList() throws ParseException {

        BankAccountDTO bankAccountDTO1 = generateBankAccountDTOData();
        BankAccountDTO bankAccountDTO2 = new BankAccountDTO();

        bankAccountDTO2.setId("c99ee5d0-47a8-4acf-8e83-1f1c686c5008");
        bankAccountDTO2.setBalance(200);
        bankAccountDTO2.setCreatedAt(df.parse(df.format(DateUtils.setMilliseconds(new Date(),0))));
        bankAccountDTO2.setStatus(AccountStatus.CREATED);
        //bankAccountDTO2.setCustomerDTO(CustomerDTODummyData.generateCustomerDTOData());

        List<BankAccountDTO> bankAccountDTOList = new ArrayList<>();
        bankAccountDTOList.add(bankAccountDTO1);
        bankAccountDTOList.add(bankAccountDTO2);

        return bankAccountDTOList;
    }
}
