package examples;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

public class TransactionProcessor {

    public void addTransactionToList(Map<String, Transaction> transactionList, Transaction transaction) {

        transactionList.put(UUID.randomUUID().toString(), transaction);
    }

    public void print(Map<String, Transaction> transactionList) {

        for(Map.Entry<String, Transaction> transaction: transactionList.entrySet()) {

            String dateTimeFormat = "yyyy/MM/dd HH:mm:ss";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateTimeFormat);

            System.out.printf(
                    "%s: %s of %.2f%n",
                    dtf.format(transaction.getValue().getTimestamp()),
                    transaction.getValue().getType(),
                    transaction.getValue().getAmount()
            );
        }
    }
}
