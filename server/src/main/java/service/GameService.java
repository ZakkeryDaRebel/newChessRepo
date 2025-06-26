package service;

import dataaccess.*;
import exception.ResponseException;
import model.AuthData;
import results.*;
import requests.*;

public class GameService {

    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws ResponseException {
        if (createGameRequest.gameName() == null || createGameRequest.authToken() == null) {
            throw new ResponseException("Error: Bad Request", 400);
        }

        AuthData auth;
        try {
            auth = authDAO.getAuth(createGameRequest.authToken());
        } catch (DataAccessException ex) {
            throw new ResponseException("Error: Unauthorized", 401);
        }

        try {
            int gameID = gameDAO.createGame(createGameRequest.gameName());
            return new CreateGameResult(gameID);
        } catch (Exception ex) {
            throw new ResponseException("Error: " + ex.getMessage(), 500);
        }
    }

    public void joinGame(JoinGameRequest joinGameRequest) throws ResponseException {
        return;
    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws ResponseException {
        return null;
    }
}
