package rzepiszczak.damian.bankingapi.dataproviders.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rzepiszczak.damian.bankingapi.core.domain.Account;
import rzepiszczak.damian.bankingapi.core.domain.Client;
import rzepiszczak.damian.bankingapi.core.exceptions.RepositoryException;
import rzepiszczak.damian.bankingapi.core.inputs.RegisterParams;
import rzepiszczak.damian.bankingapi.core.usecase.client.ClientRepository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ClientRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public Client authenticate(String login, String password) {

        String query = "select * from clients inner join accounts on clients.id = accounts.client_id where login=? and password=?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, login);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                Client client = new Client();
                client.setClientId(resultSet.getInt("clients.id"));
                client.setLogin(resultSet.getString("clients.login"));
                client.setJwt(resultSet.getString("clients.jwt"));

                Account account = new Account();
                account.setId(resultSet.getInt("accounts.id"));
                account.setClientId(client.getId());
                account.setAccountNumber(resultSet.getString("accounts.account_number"));

                client.setAccount(account);

                return client;

            } else {
                throw new RepositoryException("Client not found");
            }

        } catch (SQLException e) {
            throw new RepositoryException("Failed to create user " + e);
        }
    }

    @Override
    public Client findByLogin(String login) {

        String query = "select * from clients inner join accounts on clients.id = accounts.client_id where login=?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, login);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                Client client = new Client();
                client.setClientId(resultSet.getInt("clients.id"));
                client.setLogin(resultSet.getString("clients.login"));
                client.setJwt(resultSet.getString("clients.jwt"));

                Account account = new Account();
                account.setId(resultSet.getInt("accounts.id"));
                account.setClientId(client.getId());
                account.setAccountNumber(resultSet.getString("accounts.account_number"));

                client.setAccount(account);

                return client;

            } else {
                throw new RepositoryException("Client not found");
            }

        } catch (SQLException e) {
            throw new RepositoryException("Failed to create user " + e);
        }
    }

    @Override
    public Client create(RegisterParams registerParams) {

        String query = "insert into clients (first_name, last_name, login, password, date_of_birth) values (?,?,?,?,?)";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, registerParams.getFirstName());
            stmt.setString(2, registerParams.getLastName());
            stmt.setString(3, registerParams.getLogin());
            stmt.setString(4, registerParams.getPassword());
            stmt.setString(5, registerParams.getDayOfBirth());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                Client client = new Client();
                client.setClientId(rs.getInt(1));
                return client;
            } else {
                throw new RepositoryException("Failed to create user");
            }

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void setJWT(int clientId, String jwt) {

        String query = "update clients set jwt=? where id=?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, jwt);
            stmt.setInt(2, clientId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
