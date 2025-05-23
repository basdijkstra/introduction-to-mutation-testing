package com.ontestautomation.mutationbank;

import com.ontestautomation.mutationbank.models.AccountDto;
import com.ontestautomation.mutationbank.models.AccountType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
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

	@LocalServerPort
	int port;

	@BeforeEach
	public void createRequestSpecification() {

		requestSpec = new RequestSpecBuilder()
				.setBaseUri("http://localhost")
				.setPort(port)
				.setContentType("application/json")
				.build();
	}

	@Test
	public void createAccount() {

		AccountDto account = new AccountDto(AccountType.CHECKING);

		int accountId = given()
				.spec(requestSpec)
				.body(account)
				.when()
				.post("/account")
				.then()
				.statusCode(201)
				.extract().path("id");

		given()
				.spec(requestSpec)
				.when()
				.get("/account/" + accountId)
				.then()
				.statusCode(200)
				.body("balance", equalTo(0.0F));
	}

	@Test
	public void getAccount() {

		given()
				.spec(requestSpec)
				.when()
				.get("/account/2")
				.then()
				.statusCode(404);
	}

	@Test
	public void deleteAccount() {

		given().spec(requestSpec).when().delete("/account/5").then().statusCode(204);
	}

	@Test
	public void testDeposit() {

		AccountDto account = new AccountDto(AccountType.CHECKING);

		int accountId = given()
				.spec(requestSpec)
				.body(account)
				.when()
				.post("/account")
				.then()
				.statusCode(201)
				.extract().path("id");

		given()
				.spec(requestSpec)
				.pathParam("accountId", accountId)
				.when()
				.post("/account/{accountId}/deposit/10")
				.then()
				.statusCode(200)
				.body("balance", equalTo(10.0F));
	}

	@Test
	public void testOverdrawOnSavingsAccount() {

		AccountDto account = new AccountDto(AccountType.SAVINGS);

		int accountId = given()
				.spec(requestSpec)
				.body(account)
				.when()
				.post("/account")
				.then()
				.statusCode(201)
				.extract().path("id");

		given()
				.spec(requestSpec)
				.pathParam("accountId", accountId)
				.when()
				.post("/account/{accountId}/deposit/10")
				.then()
				.statusCode(200)
				.body("balance", equalTo(10.0F));

		given()
				.spec(requestSpec)
				.pathParam("accountId", accountId)
				.when()
				.post("/account/{accountId}/withdraw/20")
				.then()
				.statusCode(400);
	}
}
