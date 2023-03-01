package com.example.payments.exception;

import java.util.UUID;

public class SettlementPointNotFoundException extends RuntimeException {
    public SettlementPointNotFoundException(UUID message) {
        super(String.valueOf(message));
    }
}

