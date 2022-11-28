package com.kata.ebank.web;

import com.kata.ebank.dtos.BankAccountDTO;
import com.kata.ebank.dtos.CustomerDTO;
import com.kata.ebank.exceptions.CustomerNotFoundException;
import com.kata.ebank.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/bank")
@AllArgsConstructor
public class CustomerRestController {

    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> customers(){
        return new ResponseEntity<>(bankAccountService.listCustomers(), HttpStatus.OK);
    }

    @GetMapping("/customers/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomers(@RequestParam(name="keyword",defaultValue = "") String keyword){
        return new ResponseEntity<>(bankAccountService.searchCustomer(keyword), HttpStatus.OK);
    }

}
