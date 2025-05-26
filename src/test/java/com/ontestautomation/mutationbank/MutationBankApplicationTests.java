package com.ontestautomation.mutationbank;

import com.ontestautomation.mutationbank.clients.AccountClient;
import com.ontestautomation.mutationbank.logic.InterestLogic;
import com.ontestautomation.mutationbank.models.AccountDto;
import com.ontestautomation.mutationbank.models.AccountType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutationBankApplicationTests {

	private AccountClient accountClient;

	@LocalServerPort
	int port;

	@BeforeEach
	public void createRequestSpecification() {

		this.accountClient = new AccountClient("http://localhost", this.port);
	}

	@Test
	public void createNewCheckingAccount_whenRetrieved_shouldHaveZeroBalance() {

		// Create a new checking account
		AccountDto account = new AccountDto(AccountType.CHECKING);
		int accountId = this.accountClient.createAccount(account);

		// Retrieve the account to see if it has been created successfully
		Response getAccountResponse = this.accountClient.getAccount(accountId);

		// Check the response status code and the account balance (should be $0)
		Assertions.assertEquals(200, getAccountResponse.getStatusCode());
		Assertions.assertEquals(0.0F, (Float) getAccountResponse.path("balance"));
	}

	@Test
	public void getNonexistentAccount_shouldReturn404() {

		// GET an account that should not exist
		Response response = this.accountClient.getAccount(1234);

		// Check the response status code
		Assertions.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void deleteNonexistentAccount_shouldReturn204() {

		// DELETE an account that does not exist
		Response response = this.accountClient.deleteAccount(9876);

		// DELETE should always return 204, no matter if the account existed before
		Assertions.assertEquals(204, response.getStatusCode());
	}

	@Test
	public void depositIntoCheckingAccount_whenRetrieved_shouldShowUpdatedBalance() {

		// Create a new checking account
		AccountDto account = new AccountDto(AccountType.CHECKING);
		int accountId = this.accountClient.createAccount(account);

		// Deposit 10 dollars / euros / smurfs into the account
		Response response = this.accountClient.depositToAccount(accountId, 10);

		// Check that the deposit is processed correctly and that the balance is updated
		Assertions.assertEquals(200, response.getStatusCode());
		Assertions.assertEquals(10.0F, (Float) response.path("balance"));
	}

	@Test
	public void withdrawFromCheckingAccount_whenRetrieved_shouldShowUpdatedBalance() {

		// Create a new checking account
		AccountDto account = new AccountDto(AccountType.CHECKING);
		int accountId = this.accountClient.createAccount(account);

		// Deposit initial balance
		this.accountClient.depositToAccount(accountId, 200);

		// Withdraw from account
		Response response = this.accountClient.withdrawFromAccount(accountId, 150);

		// Check that the withdrawal is processed correctly and that the balance is updated
		Assertions.assertEquals(200, response.getStatusCode());
		Assertions.assertEquals(50.0F, (Float) response.path("balance"));
	}

	@Test
	public void overdrawOnSavingsAccount_shouldReturn400_shouldNotImpactBalance() {

		// Create a new savings account
		AccountDto account = new AccountDto(AccountType.SAVINGS);
		int accountId = this.accountClient.createAccount(account);

		// Deposit 10 dollars / euros / smurfs into the account
		this.accountClient.depositToAccount(accountId, 10);

		// Try to withdraw 20 dollars / euros / smurfs from the account
		// and check that this is not allowed and returns a 404
		Response response = this.accountClient.withdrawFromAccount(accountId, 20);
		Assertions.assertEquals(400, response.getStatusCode());

		// Check that the account balance is unchanged
		Response getResponse = this.accountClient.getAccount(accountId);
		Assertions.assertEquals(10.0F, (Float) getResponse.path("balance"));
	}

	@ParameterizedTest
	@CsvSource({
			"100, 101",
			"1000, 1020",
			"5000, 5150"
	})
	public void updateBalanceWithInterest_shouldReturnCorrectUpdatedBalance
			(double initialBalance, double expectedNewBalance) {

		double actualNewBalance = InterestLogic.addInterestToBalance(initialBalance);

		Assertions.assertEquals(expectedNewBalance, actualNewBalance);
	}

	@Test
	public void addInterestToSavingsAccount_shouldUpdateBalanceCorrectly() {

		// Create a new savings account
		AccountDto account = new AccountDto(AccountType.SAVINGS);
		int accountId = this.accountClient.createAccount(account);

		// Set the balance
		this.accountClient.depositToAccount(accountId, 100);

		// Add interest
		Response response = this.accountClient.addInterestToAccount(accountId);

		// Check that the balance is updated correctly
		Assertions.assertEquals(101.0F, (Float) response.path("balance"));
	}

	@Test
	public void addInterestToCheckingAccount_shouldReturn400_shouldNotImpactBalance() {

		// Create a new checking account
		AccountDto account = new AccountDto(AccountType.CHECKING);
		int accountId = this.accountClient.createAccount(account);

		// Set the balance
		this.accountClient.depositToAccount(accountId, 100);

		// Try to add interest and verify that this yields an HTTP 400
		Response response = this.accountClient.addInterestToAccount(accountId);
		Assertions.assertEquals(400, response.getStatusCode());

		// Check that the balance has not changed and still is 100
		Response getResponse = this.accountClient.getAccount(accountId);
		Assertions.assertEquals(100.0F, (Float) getResponse.path("balance"));
	}
}
