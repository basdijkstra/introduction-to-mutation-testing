package examples;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    public void deposit100_checkBalance_shouldBe100() {

        Account account = new Account(AccountType.CHECKING);

        account.deposit(100);

        assertEquals(100, account.getBalance());
    }

    @Test
    public void overdrawFromCheckingAccount_shouldBePossible() throws WithdrawException {

        Account account = new Account(AccountType.CHECKING);

        account.deposit(30);

        account.withdraw(40);

        assertEquals(-10, account.getBalance());
    }

    @Test
    public void withdrawFromSavingsAccount_shouldResultInUpdatedBalance() throws WithdrawException {

        Account account = new Account(AccountType.SAVINGS);

        account.withdraw(5);

        assertEquals(5, account.getBalance());
    }

    @Test
    public void overdrawFromSavingsAccount_shouldThrowWithdrawException() {

        Account account = new Account(AccountType.SAVINGS);

        account.deposit(30);

        assertThrows(WithdrawException.class, () -> account.withdraw(50));
    }

    @Test
    public void addInterestToCheckingAccount_shouldThrowAddInterestException() {

        Account account = new Account(AccountType.CHECKING);

        assertThrows(AddInterestException.class, account::addInterest);
    }

    @ParameterizedTest
    @CsvSource({"100, 111.1", "2000, 2050.2", "6000, 6190.3"})
    public void addInterestToSavingsAccount_shouldResultInUpdatedBalance(double amountToDeposit, double expectedNewBalance) throws AddInterestException {

        Account account = new Account(AccountType.SAVINGS);

        account.deposit(amountToDeposit);

        account.addInterest();

        assertEquals(expectedNewBalance, account.getBalance());
    }
}
