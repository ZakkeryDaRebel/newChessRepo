package dataaccess;

import model.GameData;

public interface GameDAO {
    void createGame(String gameName);
    GameData getGame(int gameID);
    void updateGame(GameData gameData);
}
