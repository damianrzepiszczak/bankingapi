package rzepiszczak.damian.bankingapi.core.usecase.client;

import rzepiszczak.damian.bankingapi.core.domain.Client;
import rzepiszczak.damian.bankingapi.core.inputs.RegisterParams;

public interface ClientService {
    Client register(RegisterParams registerParams);
    Client login(String login, String password);
}
