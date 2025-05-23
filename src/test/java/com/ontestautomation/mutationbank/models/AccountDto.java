package com.ontestautomation.mutationbank.models;

public class AccountDto {

    private AccountType type;
    private double balance;

    public AccountDto(AccountType type) {
        this.type = type;
        this.balance = 0;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
