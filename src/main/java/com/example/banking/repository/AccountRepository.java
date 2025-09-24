package com.example.banking.repository;

import com.example.banking.model.Account;
import java.util.List;

public interface AccountRepository {
    Account save(Account account);

    Account findById(String id);

    Account findByNumber(String number);

    List<Account> findByCustomerId(String customerId);

    Account findByCustomerIdAndType(String customerId, String type);
}
