package com.ontestautomation.mutationbank.dto;

import com.ontestautomation.mutationbank.models.AccountType;

public record AccountCreateUpdate(AccountType type, double balance) {
}
