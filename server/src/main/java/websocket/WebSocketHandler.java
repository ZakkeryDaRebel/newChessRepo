package websocket;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import io.javalin.websocket.*;
import model.AuthData;
import model.GameData;
import org.jetbrains.annotations.NotNull;
import websocket.commands.*;
import websocket.messages.*;

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
    public void handleMessage(@NotNull WsMessageContext ctx) {
        try {
            UserGameCommand userGameCommand = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            AuthData auth = authDAO.getAuth(userGameCommand.getAuthToken());
            if (auth == null) {
                ErrorMessage newError = new ErrorMessage("Error: Unauthorized");
                connectionManager.messageDelivery(ConnectionManager.MessageType.ROOT, 1, ctx.session, newError);
                return;
            }
            GameData game = gameDAO.getGame(userGameCommand.getGameID());
            if (game == null) {
                ErrorMessage newError = new ErrorMessage("Error: Invalid Game");
                connectionManager.messageDelivery(ConnectionManager.MessageType.ROOT, 1, ctx.session, newError);
                return;
            }
            switch (userGameCommand.getCommandType()) {
                case CONNECT: {
                    connectionManager.add(userGameCommand.getGameID(), ctx.session, auth.username());
                    String message = auth.username() + " has joined the game as ";
                    if (auth.username().equals(game.whiteUsername())) {
                        message += "the White player";
                    } else if (auth.username().equals(game.blackUsername())) {
                        message += "the Black player";
                    } else {
                        message += "an Observer";
                    }
                    LoadGameMessage loadMessage = new LoadGameMessage(game);
                    NotificationMessage notification = new NotificationMessage(message);
                    connectionManager.messageDelivery(ConnectionManager.MessageType.ROOT, 1, ctx.session, loadMessage);
                    connectionManager.messageDelivery(ConnectionManager.MessageType.NOT_ROOT, userGameCommand.getGameID(), ctx.session, notification);
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
