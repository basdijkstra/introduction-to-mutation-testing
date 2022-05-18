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

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
