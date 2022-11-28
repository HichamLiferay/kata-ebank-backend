package com.kata.ebank.repositories;


import com.kata.ebank.entities.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryUTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void find_no_customers_if_repo_is_empty(){

        Iterable<Customer> customers = customerRepository.findAll();
        assertThat(customers).isEmpty();
    }

    @Test
    public void find_customer_by_id(){

        Customer customer1 = new Customer(null,"Test 1","Test1@mail",new ArrayList<>());
        customerRepository.save(customer1);

        Customer customer2 = new Customer(null,"Test 2","Test2@mail",new ArrayList<>());
        Customer customerToBeFind = customerRepository.save(customer2);

        Customer returnedCustomer = customerRepository.findById(customerToBeFind.getId()).get();

        assertThat(returnedCustomer).isNotNull();
        assertThat(returnedCustomer).isEqualTo(customer2);

    }

    @Test
    public void save_a_customer(){

        Customer expectedCustomer = new Customer(null,"Test","Test@mail",new ArrayList<>());

        Customer returnedCustomer = customerRepository.save(expectedCustomer);

        assertThat(returnedCustomer).isNotNull();
        assertThat(returnedCustomer.getId()).isNotNull();
        assertThat(returnedCustomer.getName()).isEqualTo(expectedCustomer.getName());
        assertThat(returnedCustomer.getEmail()).isEqualTo(expectedCustomer.getEmail());

    }
}
