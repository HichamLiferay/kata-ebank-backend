package com.kata.dataset;

import com.kata.ebank.entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerEntityDummyData {

    private CustomerEntityDummyData(){}

    public static Customer generateEntityData(){

        Customer customer = new Customer();

        customer.setId(1L);
        customer.setName("name1");
        customer.setEmail("name1@gmail.com");

        return customer;
    }

    public static List<Customer> generateEntityListData(){

        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setName("name2");
        customer.setEmail("name2@gmail.com");

        customers.add(generateEntityData());
        customers.add(customer);

        return customers;
    }
}
