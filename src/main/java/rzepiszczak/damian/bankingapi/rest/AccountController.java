package rzepiszczak.damian.bankingapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rzepiszczak.damian.bankingapi.core.JwtTokenValidator;
import rzepiszczak.damian.bankingapi.core.domain.Account;
import rzepiszczak.damian.bankingapi.core.exceptions.ClientServiceException;
import rzepiszczak.damian.bankingapi.core.usecase.account.AccountService;
import rzepiszczak.damian.bankingapi.rest.presenter.AccountDTO;

@RestController
@RequestMapping(value = "/account", produces = "application/json")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{id}/deposit")
    public AccountDTO deposit(@RequestHeader("Authorization") String jwt, @PathVariable int id, @RequestParam double amount) {

        if (!JwtTokenValidator.validate(jwt)) {
            throw new ClientServiceException("User authorization failed for deposit");
        }

        Account account = accountService.deposit(id, amount);
        return new AccountDTO(account);
    }

    @PostMapping("/{id}/withdraw")
    public AccountDTO withdraw(@RequestHeader("Authorization") String jwt, @PathVariable int id, @RequestParam double amount) {

        if (!JwtTokenValidator.validate(jwt)) {
            throw new ClientServiceException("User authorization failed for withdraw");
        }

        Account account = accountService.withdraw(id, amount);
        return new AccountDTO(account);
    }

    @PostMapping("/{id}/transfer")
    public AccountDTO transfer(@RequestHeader("Authorization") String jwt, @PathVariable int id, @RequestParam int accountToId, @RequestParam double amount) {

        if (!JwtTokenValidator.validate(jwt)) {
            throw new ClientServiceException("User authorization failed for transfer");
        }

        Account account = accountService.transferTo(id, accountToId, amount);
        return new AccountDTO(account);
    }
}
