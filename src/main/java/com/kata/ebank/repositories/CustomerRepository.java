package com.kata.ebank.repositories;

import com.kata.ebank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findByNameContainsIgnoreCase(String keyword);
}
