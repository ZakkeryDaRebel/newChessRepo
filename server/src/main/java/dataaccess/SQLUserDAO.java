package dataaccess;

import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {

    public SQLUserDAO() throws DataAccessException, SQLException {
        configureDatabase();
    }

    public void createUser(String username, String password, String email) {

    }

    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    public void clearUsers() {

    }

    private String createStatement = """
        CREATE TABLE IF NOT EXISTS user (
            'username' VARCHAR(256) NOT NULL,
            'password' VARCHAR(256) NOT NULL,
            'email' VARCHAR(256) NOT NULL,
            PRIMARY KEY ('username')
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
