package examples;

public class BankingUtils {

    public static int generateAccountNumber() {

        return (int) (Math.random() * (100000 - 10000)) + 10000; // 10000-99999
    }
}
