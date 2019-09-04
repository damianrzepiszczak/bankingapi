package rzepiszczak.damian.bankingapi.dataproviders.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rzepiszczak.damian.bankingapi.core.domain.Account;
import rzepiszczak.damian.bankingapi.core.exceptions.RepositoryException;
import rzepiszczak.damian.bankingapi.core.usecase.account.AccountRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public Account create(int clientId, String accountNumber) {

        String query = "insert into accounts (account_number, balance, client_id) values (?,?,?)";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, accountNumber);
            stmt.setInt(2, 0);
            stmt.setInt(3, clientId);

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                Account account = new Account();
                account.setClientId(rs.getInt(1));
                account.setAccountNumber(accountNumber);
                return account;
            } else {
                throw new RepositoryException("Failed to create user");
            }

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Optional<Account> findById(int accountId) {

        String query = "select * from accounts where id=?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, accountId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                Account account = new Account();
                account.setId(resultSet.getInt("accounts.id"));
                account.setAccountNumber(resultSet.getString("accounts.account_number"));
                account.setBalance(resultSet.getInt("accounts.balance"));

                return Optional.of(account);

            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RepositoryException("Failed to create user " + e);
        }
    }

    @Override
    public void update(int accountId, int amount) {

        String query = "update accounts set balance=? where id=?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, amount);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void transfer(Account from, Account to, int amount) {

        String query = "update accounts set balance=? where id=?";

        Connection connection = null;

        try {

            connection = dataSource.getConnection();
            PreparedStatement stmtFrom = connection.prepareStatement(query);
            PreparedStatement stmtTo = connection.prepareStatement(query);

            connection.setAutoCommit(false);

            stmtFrom.setInt(1, from.getBalance() - amount);
            stmtFrom.setInt(2, from.getId());
            stmtFrom.executeUpdate();

            stmtTo.setInt(1, to.getBalance() + amount);
            stmtTo.setInt(2, to.getId());
            stmtTo.executeUpdate();

            connection.commit();

        } catch (SQLException e) {

            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                LOG.error("Failed to close connection after unable account money transfer");
            }

            throw new RepositoryException(e.getMessage());

        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.error("Failed to close connection after account money transfer");
            }
        }
    }
}
