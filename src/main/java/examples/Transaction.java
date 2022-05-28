package examples;

import java.time.LocalDateTime;

public class Transaction {

    private LocalDateTime timestamp;
    private TransactionType type;
    private double amount;

    public Transaction(LocalDateTime timestamp, TransactionType type, double amount) {
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}
