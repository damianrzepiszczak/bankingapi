package rzepiszczak.damian.bankingapi.core.usecase.account;

import rzepiszczak.damian.bankingapi.core.domain.Account;

import java.util.Optional;

public interface AccountRepository {
    Account create(int clientId, String accountNumber);
    Optional<Account> findById(int accountId);
    void update(int accountId, int amount);
    void transfer(Account from, Account to, int amount);
}
