package rzepiszczak.damian.bankingapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rzepiszczak.damian.bankingapi.core.domain.Client;
import rzepiszczak.damian.bankingapi.core.inputs.RegisterParams;
import rzepiszczak.damian.bankingapi.core.usecase.client.ClientService;
import rzepiszczak.damian.bankingapi.rest.presenter.ClientDTO;

@RestController
@RequestMapping(value = "/client", produces = "application/json")
public class ClientValidatorController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ClientDTO register(@RequestBody RegisterParams registerParams) {
        Client client = clientService.register(registerParams);
        return new ClientDTO(client);
    }

    @PostMapping("/login")
    public ClientDTO login(@RequestParam String login, @RequestParam String password) {
        Client client = clientService.login(login, password);
        return new ClientDTO(client);
    }
}
