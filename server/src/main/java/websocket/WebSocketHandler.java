package websocket;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import io.javalin.websocket.*;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private ConnectionManager connectionManager;

    public WebSocketHandler(AuthDAO authDAO, GameDAO gameDAO, ConnectionManager connectionManager) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.connectionManager = connectionManager;
    }

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        try {
            UserGameCommand userGameCommand = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (userGameCommand.getCommandType()) {
                case CONNECT: {
                    //Add to connection manager
                    //Get the game
                    //Create the message "username joined the game as W/B/O"
                    //Send a LoadGameMessage to the root client
                    //Send a NotificationMessage to all but the root client
                } case LEAVE: {
                    //Remove from the connection manager
                    //Get the game
                    //Remove player from the game if not observer
                    //Send a NotificationMessage to all but the root client
                } case MAKE_MOVE: {
                    MakeMoveCommand makeMoveCommand = new Gson().fromJson(ctx.message(), MakeMoveCommand.class);
                    //Get the game
                    //If the game is complete, then send an ErrorMessage to the root client and end
                    //If the move is not valid, send an ErrorMessage to root client and end
                    //If the move is valid, make the move, and update the game in the database
                    //Check to see if this would put the opponent in Checkmate, Stalemate, or Check
                    //Send a LoadGameMessage to everyone
                    //Send a NotificationMessage to all but the root user "username made the move a2 to a4"
                    //If the check was true, then send a NotificationMessage to everyone about the result of the move
                } case RESIGN: {
                    //Get the game
                    //Mark the game as complete, and update the game in the database
                    //Send a NotificationMessage to everyone saying "root client has resigned the game"
                } default: {

                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }
}
