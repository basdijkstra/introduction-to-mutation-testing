package com.ontestautomation.mutationbank.services;

import com.ontestautomation.mutationbank.exceptions.BadRequestException;
import com.ontestautomation.mutationbank.exceptions.ResourceNotFoundException;
import com.ontestautomation.mutationbank.models.Account;
import com.ontestautomation.mutationbank.models.AccountType;
import com.ontestautomation.mutationbank.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account " + id + " not found"));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public Account processDeposit(Long id, double amount) {

        if (amount <= 0) {
            throw new BadRequestException("Amount must be greater than 0, but was " + amount);
        }

        var accountPersisted = getAccount(id);

        accountPersisted.setBalance(accountPersisted.getBalance() + amount);

        return accountRepository.save(accountPersisted);
    }

    @Transactional
    public Account processWithdrawal(Long id, double amount) {

        if (amount <= 0) {
            throw new BadRequestException("Amount must be greater than 0, but was " + amount);
        }

        var accountPersisted = getAccount(id);

        if (accountPersisted.getBalance() < amount && accountPersisted.getType().equals(AccountType.SAVINGS)) {
            throw new BadRequestException("Insufficient funds to withdraw " + amount + " from savings account " + id);
        }

        accountPersisted.setBalance(accountPersisted.getBalance() - amount);

        return accountRepository.save(accountPersisted);
    }

    @Transactional
    public Account addInterest(Long id) {

        var accountPersisted = getAccount(id);

        if (accountPersisted.getType().equals(AccountType.CHECKING)) {
            throw new BadRequestException("Cannot add interest to checking account " + id);
        }

        double currentBalance = accountPersisted.getBalance();

        if (currentBalance < 1000) {
            accountPersisted.setBalance(currentBalance * 1.01);
        }
        else if (currentBalance < 5000) {
            accountPersisted.setBalance(currentBalance * 1.02);
        }
        else {
            accountPersisted.setBalance(currentBalance * 1.03);
        }

        return accountRepository.save(accountPersisted);
    }

    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
