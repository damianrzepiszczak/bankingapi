package rzepiszczak.damian.bankingapi.core.usecase.account;

import rzepiszczak.damian.bankingapi.core.domain.Account;

public interface AccountService {
    Account deposit(int accountId, double amount);
    Account withdraw(int accountId, double amount);
    Account transferTo(int accountIdFrom, int accountIdTo, double amount);
}
