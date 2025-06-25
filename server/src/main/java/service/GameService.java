package service;

import dataaccess.*;
import results.*;
import requests.*;

public class GameService {

    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) {
        return null;
    }

    public void joinGame(JoinGameRequest joinGameRequest) {
        return;
    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) {
        return null;
    }
}
