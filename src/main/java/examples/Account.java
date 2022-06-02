package examples;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Account {

    private final AccountType type;
    private final int number;
    private double balance;

    private final Map<String, Transaction> transactionList = new HashMap<String, Transaction>();
    private final TransactionProcessor transactionProcessor = new TransactionProcessor();

    public Account(AccountType type) {

        this.type = type;
        this.number = 12345;

        // New savings accounts are loaded with $10 by default
        this.balance = type.equals(AccountType.SAVINGS) ? 10 : 0;
    }

    public double getBalance() {

        return this.balance;
    }

    public Map<String, Transaction> getTransactionList() {

        return this.transactionList;
    }

    public void deposit(double amountToDeposit) {

        this.balance += amountToDeposit;

        transactionProcessor.addTransactionToList(
                this.transactionList,
                new Transaction(
                        LocalDateTime.now(),
                        TransactionType.DEPOSIT,
                        amountToDeposit
                )
        );
    }

    public void withdraw(double amountToWithdraw) throws WithdrawException {

        if (amountToWithdraw > this.balance && this.type.equals(AccountType.SAVINGS)) {
            throw new WithdrawException("Cannot overdraw on a savings account");
        }

        this.balance -= amountToWithdraw;

        transactionProcessor.addTransactionToList(
                this.transactionList,
                new Transaction(
                        LocalDateTime.now(),
                        TransactionType.WITHDRAWAL,
                        amountToWithdraw
                )
        );
    }

    public void addInterest() throws AddInterestException {

        if (this.type.equals(AccountType.CHECKING)) {
            throw new AddInterestException("Cannot add interest to a checking account");
        }

        if (this.balance < 1000) {
            this.balance *= 1.01; // 1% interest
        }
        else if (this.balance < 5000) {
            this.balance *= 1.02; // 2% interest
        }
        else {
            this.balance *= 1.03; // 3% interest
        }
    }
}
