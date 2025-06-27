package dataaccess;

import exception.ResponseException;
import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class SQLAuthDAO implements AuthDAO {

    public SQLAuthDAO() throws DataAccessException, SQLException {
        configureDatabase();
    }

    public void createAuth(String username, String authToken) throws ResponseException {
        String statement = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(0, authToken);
                ps.setString(1, username);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new ResponseException("Error: Failed to create Auth", 500);
        } catch (DataAccessException ex) {
            throw new ResponseException("Error: Database failed to connect", 500);
        }
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    public void deleteAuth(String authToken) throws DataAccessException {

    }

    public void clearAuths() {

    }

    private final String createStatement =
            """
            CREATE TABLE IF NOT EXISTS auth (
              'authToken' VARCHAR(256) NOT NULL,
              'username' VARCHAR(256) NOT NULL,
              PRIMARY KEY ('authToken')
            )
            """;


    public void configureDatabase() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(createStatement)) {
                ps.executeUpdate();
            }
        }
    }
}
