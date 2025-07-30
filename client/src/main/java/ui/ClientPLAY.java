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

    public Boolean isObserver() {
        return isObserver;
    }

    public void setGame(GameData game) {
        gameData = game;
    }

    public String playEval(Scanner scan, String input) {
        if (input.equals("2") || input.equalsIgnoreCase("L") || input.equalsIgnoreCase("Leave")) {
            input = "leave";
            return "leave";
        } else if (input.equals("3") || input.equalsIgnoreCase("I") || input.equalsIgnoreCase("Highlight")) {
            return "Message: Not implemented yet";
        } else if (input.equals("4") || input.equalsIgnoreCase("D") || input.equalsIgnoreCase("Draw")) {
            return "Message: Not implemented yet";
        } else if (!isObserver && input.equals("5") || input.equalsIgnoreCase("M") || input.equalsIgnoreCase("Move")) {
            return "Message: Not implemented yet";
        } else if (!isObserver && input.equals("6") || input.equalsIgnoreCase("R") || input.equalsIgnoreCase("Resign")) {
            return "Message: Not implemented yet";
        } else {
            return "invalid input";
        }
    }
}
