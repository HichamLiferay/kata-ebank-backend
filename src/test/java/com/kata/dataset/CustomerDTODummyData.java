package com.kata.dataset;

import com.kata.ebank.dtos.CustomerDTO;

import java.util.ArrayList;
import java.util.List;

public class CustomerDTODummyData {

    private CustomerDTODummyData(){}

    public static CustomerDTO generateCustomerDTOData(){

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId(1L);
        customerDTO.setName("any name");
        customerDTO.setEmail("randomemail@gmail.com");

        return customerDTO;
    }

    public static List<CustomerDTO> generateCustomerDTOList(){

        List<CustomerDTO> customerDTOList = new ArrayList<>();

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setId(1L);
        customerDTO1.setName("any name 1");
        customerDTO1.setEmail("randomemail1@gmail.com");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setId(2L);
        customerDTO2.setName("only a name 2");
        customerDTO2.setEmail("randomemail2@gmail.com");

        customerDTOList.add(customerDTO1);
        customerDTOList.add(customerDTO2);

        return customerDTOList;
    }
}
