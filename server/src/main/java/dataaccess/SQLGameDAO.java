package dataaccess;

import exception.ResponseException;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class SQLGameDAO implements GameDAO {

    public SQLGameDAO() throws DataAccessException, SQLException {
        configureDatabase();
    }

    public int createGame(String gameName) {
        return 0;
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    public Collection<GameData> listGames() {
        return List.of();
    }

    public void updateGame(GameData gameData) throws DataAccessException {

    }

    public void clearGames() {

    }

    private String createStatement = """
        CREATE TABLE IF NOT EXISTS game (
            'gameID' INT NOT NULL,
            'whiteUsername' VARCHAR(256) DEFAULT NULL,
            'blackUsername' VARCHAR(256) DEFAULT NULL,
            'gameName' VARCHAR(256) NOT NULL,
            'game' VARCHAR(256) NOT NULL
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
