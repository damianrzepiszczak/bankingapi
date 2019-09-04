package rzepiszczak.damian.bankingapi.rest.presenter;

import rzepiszczak.damian.bankingapi.core.domain.Account;

public class AccountDTO {

    private int id;
    private String number;
    private double balance;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getAccountNumber();
        this.balance = account.getBalance() / 100.0;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }
}
