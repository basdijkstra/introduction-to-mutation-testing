package com.ontestautomation.mutationbank.controllers;

import com.ontestautomation.mutationbank.dto.AccountCreateUpdate;
import com.ontestautomation.mutationbank.models.Account;
import com.ontestautomation.mutationbank.services.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody AccountCreateUpdate accountCreate) {

        Account account = new Account();
        BeanUtils.copyProperties(accountCreate, account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(account));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable(value = "id") Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(id));
    }

    @PostMapping(path = "/{id}/deposit/{amount}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> depositToAccount(@PathVariable("id") Long id, @PathVariable("amount") double amount) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.processDeposit(id, amount));
    }

    @PostMapping(path = "/{id}/withdraw/{amount}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> withdrawFromAccount(@PathVariable("id") Long id, @PathVariable("amount") double amount) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.processWithdrawal(id, amount));
    }

    @PostMapping(path = "/{id}/interest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> addInterestToAccount(@PathVariable("id") Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.addInterest(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Account>> getAllAccounts() {

        List<Account> accounts = accountService.getAllAccounts();

        if (accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable(value = "id") Long id) {

        accountService.deleteAccount(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
