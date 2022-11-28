package com.kata.ebank.web;

import com.kata.ebank.dtos.*;
import com.kata.ebank.exceptions.BalanceNotSufficientException;
import com.kata.ebank.exceptions.BankAccountNotFoundException;
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
public class BankAccountRestController {

    private BankAccountService bankAccountService;

    @GetMapping("/accounts/customer/{customerId}")
    public ResponseEntity<List<BankAccountDTO>> getBankAccountsByCustomerId(@PathVariable(name = "customerId") Long customerId) throws CustomerNotFoundException {
        return new ResponseEntity<>(bankAccountService.listBankAccountByCustomerId(customerId),HttpStatus.OK);
    }

    @GetMapping("/accounts/{accountId}/operations")
    public ResponseEntity<AccountHistoryDTO> getAccountHistory(@PathVariable(name = "accountId") String accountId,
                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return new ResponseEntity<>(bankAccountService.getBankAccountHistory(accountId,page,size),HttpStatus.OK);
    }

    @PostMapping("/accounts/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public void deposit(@RequestBody OperationRequestDTO operationRequestDTO) throws BankAccountNotFoundException {
        bankAccountService.deposit(operationRequestDTO.getAccountId(), operationRequestDTO.getAmount(), operationRequestDTO.getDescription());
    }

    @PostMapping("/accounts/withdrawal")
    @ResponseStatus(HttpStatus.CREATED)
    public void withdrawal(@RequestBody OperationRequestDTO operationRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.withdrawal(operationRequestDTO.getAccountId(), operationRequestDTO.getAmount(), operationRequestDTO.getDescription());
    }

}
