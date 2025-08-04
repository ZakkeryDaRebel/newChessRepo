package server;

import com.google.gson.Gson;
import dataaccess.*;
import exception.ResponseException;
import handler.Handler;
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

    private Handler handler;

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

        handler = new Handler(clearService, gameService, userService);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
        // Register your endpoints and exception handlers here.
                .delete("/db", context -> {handler.clearHandler(context);})
                .delete("/session", context -> {handler.logoutHandler(context);})
                .get("/game", context -> {handler.listGamesHandler(context);})
                .post("/user", context -> {handler.registerHandler(context);})
                .post("/session", context -> {handler.loginHandler(context);})
                .post("/game", context -> {handler.createGameHandler(context);})
                .put("/game", context -> {handler.joinGameHandler(context);})
                .exception(ResponseException.class, (e, context) -> {handler.exceptionHandler(e, context);})
        ;
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
