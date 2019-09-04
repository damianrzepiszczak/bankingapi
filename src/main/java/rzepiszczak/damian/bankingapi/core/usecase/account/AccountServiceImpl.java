package rzepiszczak.damian.bankingapi.core.usecase.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rzepiszczak.damian.bankingapi.core.domain.Account;
import rzepiszczak.damian.bankingapi.core.exceptions.RepositoryException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account deposit(int accountId, double amount) {

        Optional<Account> optAccount = accountRepository.findById(accountId);

        if (optAccount.isPresent()) {
            Account account = optAccount.get();
            account.setBalance(account.getBalance() + (int) (amount * 100));
            accountRepository.update(accountId, account.getBalance());
            return account;
        } else {
            throw new RepositoryException("Account not found");
        }
    }

    @Override
    public Account withdraw(int accountId, double amount) {

        Optional<Account> optAccount = accountRepository.findById(accountId);

        if (optAccount.isPresent()) {

            Account account = optAccount.get();

            if (account.getBalance() - (int) (amount * 100) >= 0) {
                account.setBalance(account.getBalance() - (int) (amount * 100));
                accountRepository.update(accountId, account.getBalance());
                return account;
            } else {
                throw new RepositoryException("Not enough money on account");
            }

        } else {
            throw new RepositoryException("Account not found");
        }
    }

    @Override
    @Transactional
    public Account transferTo(int accountIdFrom, int accountIdTo, double amount) {

        Account accountFrom = accountRepository.findById(accountIdFrom).orElseThrow(() -> new RepositoryException("Account from not found"));
        Account accountTo = accountRepository.findById(accountIdTo).orElseThrow(() -> new RepositoryException("Account to not found"));

        if (accountFrom.getBalance() - (int) (amount * 100) < 0 ) {
            throw new RepositoryException("Not enough money on account");
        }

        accountRepository.transfer(accountFrom, accountTo, (int) (amount * 100));
        accountFrom.setBalance(accountFrom.getBalance() - (int) (amount * 100));

        return accountFrom;
    }
}
