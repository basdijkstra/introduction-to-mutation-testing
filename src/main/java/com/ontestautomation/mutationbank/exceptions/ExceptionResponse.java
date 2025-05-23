package com.ontestautomation.mutationbank.exceptions;

import java.time.Instant;

public record ExceptionResponse(Instant data, String message, String details) {
}