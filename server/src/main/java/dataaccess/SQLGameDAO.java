package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.List;

public class SQLGameDAO implements GameDAO {
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
}
