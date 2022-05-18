package examples;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Account {

    private final AccountType type;
    private final int number;
    private double balance;

    private Map<String, Transaction> transactionList = new HashMap<String, Transaction>();
    private TransactionProcessor transactionProcessor = new TransactionProcessor();

    private boolean interestAdded;

    public Account(AccountType type) {

        this.type = type;
        this.number = 12345;
        this.balance = 0;

        this.interestAdded = false;
    }

    public double getBalance() {

        return this.balance;
    }

    public void deposit(double amountToDeposit) {

        this.balance += amountToDeposit;

        transactionProcessor.addTransactionToList(new Transaction(TransactionType.DEPOSIT, amountToDeposit));
    }

    public void withdraw(double amountToWithdraw) throws WithdrawException {

        if (amountToWithdraw > this.balance && this.type.equals(AccountType.SAVINGS)) {
            throw new WithdrawException("Cannot overdraw on a savings account");
        }

        this.balance -= amountToWithdraw;

        transactionProcessor.addTransactionToList(new Transaction(TransactionType.WITHDRAWAL, amountToWithdraw));
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

        setInterestAdded(true);
    }

    private void setInterestAdded(boolean value) {

        this.interestAdded = value;
    }

    private class TransactionProcessor {

        public void addTransactionToList(Transaction transaction) {

            String dateTimeFormat = "yyyy/MM/dd HH:mm:ss";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateTimeFormat);

            transactionList.put(dtf.format(LocalDateTime.now()), transaction);
        }
    }
}
