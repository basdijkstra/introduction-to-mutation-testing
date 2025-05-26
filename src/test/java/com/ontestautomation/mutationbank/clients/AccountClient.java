package com.ontestautomation.mutationbank.clients;

import com.ontestautomation.mutationbank.models.AccountDto;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AccountClient extends AccountBase {

    public AccountClient(String baseUri, int port) {

        super(baseUri, port);
    }

    public int createAccount(AccountDto account) {

        return given()
                .spec(super.requestSpec())
                .body(account)
                .post("/account")
                .then()
                .statusCode(201)
                .extract().path("id");
    }

    public Response getAccount(int accountId) {

        return given()
                .spec(super.requestSpec())
                .pathParam("accountId", accountId)
                .when()
                .get("/account/{accountId}")
                .then()
                .extract().response();
    }

    public Response getAllAccounts() {

        return given()
                .spec(super.requestSpec())
                .when()
                .get("/account")
                .then()
                .extract().response();
    }

    public Response addInterestToAccount(int accountId) {

        return given()
                .spec(super.requestSpec())
                .pathParam("accountId", accountId)
                .when()
                .post("/account/{accountId}/interest")
                .then()
                .extract().response();
    }

    public Response deleteAccount(int accountId) {

        return given()
                .spec(super.requestSpec())
                .pathParam("accountId", accountId)
                .when()
                .delete("/account/{accountId}")
                .then()
                .extract().response();
    }

    public Response depositToAccount(int accountId, double amount) {

        return given()
                .spec(super.requestSpec())
                .pathParam("accountId", accountId)
                .pathParam("amount", amount)
                .when()
                .post("/account/{accountId}/deposit/{amount}")
                .then()
                .extract().response();
    }

    public Response withdrawFromAccount(int accountId, double amount) {

        return given()
                .spec(super.requestSpec())
                .pathParam("accountId", accountId)
                .pathParam("amount", amount)
                .when()
                .post("/account/{accountId}/withdraw/{amount}")
                .then()
                .extract().response();
    }
}
