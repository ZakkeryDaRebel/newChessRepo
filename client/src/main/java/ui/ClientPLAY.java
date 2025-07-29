package ui;

import chess.ChessGame;
import connection.ServerFacade;
import model.GameData;

import java.util.Scanner;

public class ClientPLAY {

    private ServerFacade serverFacade;
    private GameData gameData;
    private ChessGame.TeamColor playColor;
    private boolean isObserver;

    public ClientPLAY(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    public void setPlayColor(ChessGame.TeamColor color) {
        playColor = color;
    }

    public void setObserver(boolean isObserver) {
        this.isObserver = isObserver;
    }

    public void setGame(GameData game) {
        gameData = game;
    }

    public String playEval(Scanner scan, String input) {
        return "empty";

        /*
         Enter \"1\", \"H\", or \"Help\" to show this list of actions you can take again"
                    + "\n" + " - Enter \"2\", \"L\", or \"Leave\" to leave the game and return to signed in state"
                    + "\n" + " - Enter \"3\", \"I\", or \"Highlight\" to highlight the legal moves for a chess piece"
                    + "\n" + "         (You will need to supply the row and column of the piece you want to check)"
                    + "\n" + " - Enter \"4\", \"D\", or \"Draw\" to redraw the chess board"
                    + "\n" + " ~ The options below are only if you are playing in the game, not observing the game"
                    + "\n" + " - Enter \"5\", \"M\", or \"Move\" to move a chess piece"
                    + "\n" + "         (You will need to supply the row and column of the piece you want to move,"
                    + "\n" + "           and the row and column of where you want to move the piece to)"
                    + "\n" + " - Enter \"6\", \"R\", or \"Resign\" to r
         */
    }
}
