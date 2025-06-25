package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
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

    //private ClearHandler clearHandler;

    public Server() {
        //Memory Based DAOs
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        userDAO = new MemoryUserDAO();

        //Create Services
        clearService = new ClearService(authDAO, gameDAO, userDAO);
        gameService = new GameService(authDAO, gameDAO);
        userService = new UserService(authDAO, userDAO);

        //Create Handlers
        //clearHandler = new ClearHandler(clearService);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
        // Register your endpoints and exception handlers here.
                .delete("/db", this::clearHandler)
                .delete("/session", this::logoutHandler)
                .get("/game", this::listGamesHandler)
                .post("/user", this::registerHandler)
                .post("/session", this::loginHandler)
                .post("/game", this::createGameHandler)
                .put("/game", this::joinGameHandler)
                .exception(Exception.class, this::exceptionHandler)
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
    private void exceptionHandler(Exception ex, Context ctx) {
        //Set exception status
        ctx.status();
        ctx.json(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

    private void clearHandler(Context ctx) throws Exception {
        clearService.clear();
        ctx.status(200);
    }

    private void createGameHandler(Context ctx) throws Exception {

    }

    private void joinGameHandler(Context ctx) throws Exception {

    }

    private void listGamesHandler(Context ctx) throws Exception {

    }

    private void loginHandler(Context ctx) throws Exception {

    }

    private void logoutHandler(Context ctx) throws Exception {

    }

    private void registerHandler(Context ctx) throws Exception {

    }
}
