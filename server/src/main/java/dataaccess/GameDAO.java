package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    void createGame(String gameName);
    GameData getGame(int gameID) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    void updateGame(GameData gameData) throws DataAccessException;
    void clearGames();
}
