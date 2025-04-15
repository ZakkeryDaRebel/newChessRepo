package server;

import spark.*;

public class Server {

    //RegisterHandler registerHandler;
    //LoginHandler loginHandler;
    //LogoutHandler logoutHandler;
    //CreateGameHandler createGameHandler;
    //ListGamesHandler listGamesHandler;
    //JoinGameHandler joinGameHandler;
    //clearHandler clearHandler;

    public Server() {

        //registerHandler = new RegisterHandler();
        //loginHandler = new LoginHandler();
        //logoutHandler = new LogoutHandler();
        //createGameHandler = new CreateGameHandler();
        //listGamesHandler = new ListGamesHandler();
        //joinGameHandler = new JoinGameHandler();
        //clearHandler = new clearHandler();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
