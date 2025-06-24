package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {

    HashMap<Integer, GameData> games;
    int nextGameID;

    public MemoryGameDAO() {
        games = new HashMap<>();
        nextGameID = 101;
    }

    public void createGame(String gameName) {
        GameData newGame = new GameData(nextGameID, null, null, gameName, new ChessGame());
        games.put(nextGameID++, newGame);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        GameData game = games.get(gameID);
        if (game == null) {
            throw new DataAccessException("Error: Invalid gameID");
        }
        return game;
    }

    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> allGames = games.values();
        if (allGames.isEmpty()) {
            throw new DataAccessException("Error: No games");
        }
        return allGames;
    }

    public void updateGame(GameData gameData) throws DataAccessException {
        GameData oldGame = games.get(gameData.gameID());
        if(oldGame == null) {
            throw new DataAccessException("Error: Game does not exist");
        }
        games.put(gameData.gameID(), gameData);
    }

    public void clearGames() {
        games.clear();
    }
}
