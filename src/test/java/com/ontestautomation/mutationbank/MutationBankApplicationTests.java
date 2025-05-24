package com.ontestautomation.mutationbank;

import com.ontestautomation.mutationbank.clients.AccountClient;
import com.ontestautomation.mutationbank.models.AccountDto;
import com.ontestautomation.mutationbank.models.AccountType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutationBankApplicationTests {

	private RequestSpecification requestSpec;

	private AccountClient accountClient;

	@LocalServerPort
	int port;

	@BeforeEach
	public void createRequestSpecification() {

		this.accountClient = new AccountClient("http://localhost", this.port);
	}

	@Test
	public void createAccount() {

		AccountDto account = new AccountDto(AccountType.CHECKING);

		int accountId = this.accountClient.createAccount(account);

		Response getAccountResponse = this.accountClient.getAccount(accountId);

		Assertions.assertEquals(200, getAccountResponse.getStatusCode());
		Assertions.assertEquals(0.0F, (Float) getAccountResponse.path("balance"));
	}

	@Test
	public void getAccount() {

		Response response = this.accountClient.getAccount(1234);

		Assertions.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void deleteAccount() {

		Response response = this.accountClient.deleteAccount(9876);

		Assertions.assertEquals(204, response.getStatusCode());
	}

	@Test
	public void testDeposit() {

		AccountDto account = new AccountDto(AccountType.CHECKING);

		int accountId = this.accountClient.createAccount(account);

		Response response = this.accountClient.depositToAccount(accountId, 10);

		Assertions.assertEquals(200, response.getStatusCode());
		Assertions.assertEquals(10.0F, (Float) response.path("balance"));
	}

	@Test
	public void testOverdrawOnSavingsAccount() {

		AccountDto account = new AccountDto(AccountType.SAVINGS);

		int accountId = this.accountClient.createAccount(account);

		this.accountClient.depositToAccount(accountId, 10);

		Response response = this.accountClient.withdrawFromAccount(accountId, 20);

		Assertions.assertEquals(400, response.getStatusCode());
	}
}
