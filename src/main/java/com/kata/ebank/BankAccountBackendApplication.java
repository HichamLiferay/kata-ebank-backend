package com.kata.ebank;

import com.kata.ebank.entities.BankAccount;
import com.kata.ebank.entities.Customer;
import com.kata.ebank.enums.AccountStatus;
import com.kata.ebank.repositories.BankAccountRepository;
import com.kata.ebank.repositories.CustomerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
@ComponentScan
public class BankAccountBackendApplication {


	public static void main(String[] args) {
		SpringApplication.run(BankAccountBackendApplication.class, args);
	}

	@Profile("dev")
	@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository){
		return args -> {

			Stream.of("Hicham","Anis").forEach(name ->{
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(customer -> {
				BankAccount bankAccount = new BankAccount();
				bankAccount.setId(UUID.randomUUID().toString());
				bankAccount.setCustomer(customer);
				bankAccount.setCreatedAt(new Date());
				bankAccount.setBalance(0);
				bankAccount.setStatus(AccountStatus.CREATED);
				bankAccountRepository.save(bankAccount);
			});
		};
	}
}
