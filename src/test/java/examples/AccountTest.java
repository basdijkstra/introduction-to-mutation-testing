package examples;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    public void createAccount_numberShouldBeBetween10000And99999() {

        Account account = new Account(AccountType.CHECKING);

        assertTrue(10000 <= account.getNumber() && account.getNumber() <= 99999 );
    }

    @Test
    public void deposit100_checkBalance_shouldBe100() {

        Account account = new Account(AccountType.CHECKING);

        account.deposit(100);

        assertEquals(100, account.getBalance());
    }

    @Test
    public void overdrawFromCheckingAccount_shouldBePossible() throws WithdrawException{

        Account account = new Account(AccountType.CHECKING);

        account.deposit(30);

        account.withdraw(40);

        assertEquals(-10, account.getBalance());
    }

    @Test
    public void overdrawFromSavingsAccount_shouldThrowWithdrawException() {

        Account account = new Account(AccountType.SAVINGS);

        account.deposit(30);

        assertThrows(WithdrawException.class, () -> account.withdraw(40));
    }
}
