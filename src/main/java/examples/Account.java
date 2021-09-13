package examples;

public class Account {

    private final AccountType type;
    private final int number;
    private double balance;

    public Account(AccountType type) {

        this.type = type;
        this.number = (int) (Math.random() * (100000 - 10000)) + 10000; // 10000-99999
        this.balance = 0;
    }

    public int getNumber() {

        return this.number;
    }

    public double getBalance() {

        return this.balance;
    }

    public void deposit(double amountToDeposit) {

        this.balance += amountToDeposit;
    }

    public void withdraw(double amountToWithdraw) throws WithdrawException {

        if (amountToWithdraw > this.balance && this.type.equals(AccountType.SAVINGS)) {
            throw new WithdrawException("Cannot overdraw on a savings account");
        }

        this.balance -= amountToWithdraw;
    }

    public void addInterest(double interestRate) {

        if (this.type.equals(AccountType.SAVINGS)) {
            this.balance *= (1 + interestRate);
        }
    }
}
