package rzepiszczak.damian.bankingapi.rest.presenter;

import rzepiszczak.damian.bankingapi.core.domain.Client;

public class ClientDTO {

    private int clientId;
    private String login;
    private String jwtToken;
    private String accountNumber;
    private int accountId;

    public ClientDTO(Client client) {
        this.clientId = client.getId();
        this.login = client.getLogin();
        this.jwtToken = client.getJwt();
        this.accountNumber = client.getAccount().getAccountNumber();
        this.accountId = client.getAccount().getId();
    }

    public int getClientId() {
        return clientId;
    }

    public String getLogin() {
        return login;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getAccountId() {
        return accountId;
    }
}
