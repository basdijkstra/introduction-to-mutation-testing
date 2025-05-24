package com.ontestautomation.mutationbank.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public abstract class AccountBase {

    private final String baseUri;
    private final int port;

    protected AccountBase(String baseUri, int port) {

        this.baseUri = baseUri;
        this.port = port;
    }

    public RequestSpecification requestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(this.baseUri)
                .setPort(this.port)
                .setContentType("application/json")
                .build();
    }
}
