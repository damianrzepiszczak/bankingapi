package rzepiszczak.damian.bankingapi.core.usecase.client;

import rzepiszczak.damian.bankingapi.core.domain.Client;
import rzepiszczak.damian.bankingapi.core.inputs.RegisterParams;

public interface ClientRepository {
    Client authenticate(String login, String password);
    Client findByLogin(String login);
    Client create(RegisterParams registerParams);
    void setJWT(int clientId, String jwt);
}
