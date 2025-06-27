package server;

import com.google.gson.Gson;
import dataaccess.*;
import exception.ResponseException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import requests.*;
import results.*;
import service.*;
import java.util.Map;

public class Server {

    private final Javalin javalin;

    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;

    private ClearService clearService;
    private GameService gameService;
    private UserService userService;

    public Server() {
        try {
            //First try to create SQL Based DAOs
            authDAO = new SQLAuthDAO();
            gameDAO = new SQLGameDAO();
            userDAO = new SQLUserDAO();
            System.out.println("Server is using SQL databases");
        } catch (Exception ex) {
            //Memory Based DAOs if MySQL ones failed
            authDAO = new MemoryAuthDAO();
            gameDAO = new MemoryGameDAO();
            userDAO = new MemoryUserDAO();
            System.out.println("Server is using Memory databases");
        }

        //Create Services
        clearService = new ClearService(authDAO, gameDAO, userDAO);
        gameService = new GameService(authDAO, gameDAO);
        userService = new UserService(authDAO, userDAO);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
        // Register your endpoints and exception handlers here.
                .delete("/db", this::clearHandler)
                .delete("/session", this::logoutHandler)
                .get("/game", this::listGamesHandler)
                .post("/user", this::registerHandler)
                .post("/session", this::loginHandler)
                .post("/game", this::createGameHandler)
                .put("/game", this::joinGameHandler)
                .exception(ResponseException.class, this::exceptionHandler)
        ;
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    //Handlers
    private void exceptionHandler(ResponseException ex, Context ctx) {
        ctx.status(ex.getStatus());
        ctx.json(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

    private void clearHandler(Context ctx) throws ResponseException {
        clearService.clear();
        successHandler(ctx, "");
    }

    private void createGameHandler(Context ctx) throws ResponseException {
        String authToken = ctx.header("Authorization");
        CreateGameRequest createGameRequest = new Gson().fromJson(ctx.body(), CreateGameRequest.class);
        createGameRequest = new CreateGameRequest(authToken, createGameRequest.gameName());

        CreateGameResult createGameResult = gameService.createGame(createGameRequest);
        successHandler(ctx, new Gson().toJson(createGameResult));
    }

    private void joinGameHandler(Context ctx) throws ResponseException {
        String authToken = ctx.header("Authorization");
        JoinGameRequest joinGameRequest = new Gson().fromJson(ctx.body(), JoinGameRequest.class);
        joinGameRequest = new JoinGameRequest(authToken, joinGameRequest.playerColor(), joinGameRequest.gameID());

        gameService.joinGame(joinGameRequest);
        successHandler(ctx, "");
    }

    private void listGamesHandler(Context ctx) throws ResponseException {
        String authToken = ctx.header("Authorization");
        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);

        ListGamesResult listGamesResult = gameService.listGames(listGamesRequest);
        successHandler(ctx, new Gson().toJson(listGamesResult));
    }

    private void loginHandler(Context ctx) throws ResponseException {
        LoginRequest loginRequest = new Gson().fromJson(ctx.body(), LoginRequest.class);

        LoginResult loginResult = userService.login(loginRequest);
        successHandler(ctx, new Gson().toJson(loginResult));
    }

    private void logoutHandler(Context ctx) throws ResponseException {
        String authToken = ctx.header("Authorization");
        LogoutRequest logoutRequest = new LogoutRequest(authToken);

        userService.logout(logoutRequest);
        successHandler(ctx, "");
    }

    private void registerHandler(Context ctx) throws ResponseException {
        RegisterRequest registerRequest = new Gson().fromJson(ctx.body(), RegisterRequest.class);

        RegisterResult registerResult = userService.register(registerRequest);
        successHandler(ctx, new Gson().toJson(registerResult));
    }

    private void successHandler(Context ctx, String json) {
        ctx.status(200);
        ctx.json(json);
    }
}
