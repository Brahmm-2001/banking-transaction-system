package com.example.banking.factory;

import com.example.banking.model.Account;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountFactory {

    public static Account createAccount(String customerId, BigDecimal initialDeposit, String type) {
        String t = type.toUpperCase();
        if (!t.equals("SAVINGS") && !t.equals("CURRENT")) {
            throw new IllegalArgumentException("Invalid account type. Must be SAVINGS or CURRENT");
        }

        String id = UUID.randomUUID().toString();
        String accountNumber = "ACC" + System.currentTimeMillis();
        return new Account(id, customerId, accountNumber, initialDeposit, t);
    }
}
