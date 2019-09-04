package rzepiszczak.damian.bankingapi.core.usecase.client;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rzepiszczak.damian.bankingapi.core.domain.Client;
import rzepiszczak.damian.bankingapi.core.exceptions.ClientServiceException;
import rzepiszczak.damian.bankingapi.core.inputs.RegisterParams;
import rzepiszczak.damian.bankingapi.core.usecase.account.AccountRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public Client register(RegisterParams registerParams) {

        System.out.println(registerParams);

        if(!registerParams.getPassword().equals(registerParams.getConfirmPassword()) || !registerParams.isAcceptedPersonalData()) {
            throw new ClientServiceException(("User registration failed"));
        }

        Client client = clientRepository.create(registerParams);
        accountRepository.create(client.getId(), UUID.randomUUID().toString());
        clientRepository.setJWT(client.getId(), generateJWT(client));

        return clientRepository.findByLogin(registerParams.getLogin());
    }

    @Override
    public Client login(String login, String password) {
        return clientRepository.authenticate(login, password);
    }

    private String generateJWT(Client client) {

        return Jwts.builder()
                .setSubject(client.getLogin())
                .claim("id", client.getId())
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
    }
}
