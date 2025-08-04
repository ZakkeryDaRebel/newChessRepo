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
    private DrawBoard drawBoard;

    public ClientPLAY(ServerFacade serverFacade, DrawBoard drawBoard) {
        this.serverFacade = serverFacade;
        this.drawBoard = drawBoard;
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

    public void setGameInfo(GameData game) {
        gameData = game;
    }

    public void setGame(ChessGame game) {
        gameData = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), game);
    }

    public String playEval(Scanner scan, String input) {
        if (input.equals("2") || input.equalsIgnoreCase("L") || input.equalsIgnoreCase("Leave")) {
            input = "leave";
            return "leave";
        } else if (input.equals("3")) {
            System.out.println(" Would you like your chess pieces to look like the chess piece icons, or use a text letter?");
            System.out.println(" - Enter \"1\", \"P\", or \"Piece\" if you would like your chess pieces to look like \'" + EscapeSequences.BLACK_QUEEN + "\'");
            System.out.println(" - Enter \"2\", \"T\", or \"Text\" if you would like your chess pieces to look like \'Q\'");
            printPrompt();
            String choice = scan.nextLine();
            if (choice.equals("1") || choice.equalsIgnoreCase("P") || choice.equalsIgnoreCase("Piece")) {
                drawBoard.setPieceFont(true);
            } else if (choice.equals("2") || choice.equalsIgnoreCase("T") || choice.equalsIgnoreCase("Text")) {
                drawBoard.setPieceFont(false);
            } else {
                return "invalid input";
            }
            return "";
        } else if (input.equals("4") || input.equalsIgnoreCase("C") || input.equalsIgnoreCase("Color")) {
            System.out.println(" Please select the board color format that you would like to play with");
            drawBoard.printColorOptions();
            printPrompt();
            String choice = scan.nextLine();
            if (choice.equals("1")) {
                drawBoard.setColorFormat(0);
            } else if (choice.equals("2")) {
                drawBoard.setColorFormat(1);
            } else {
                return "invalid input";
            }
            return "Message: Successfully updated the color";
        } else if (input.equals("5") || input.equalsIgnoreCase("I") || input.equalsIgnoreCase("Highlight")) {
            return "Message: Not implemented yet";
        } else if (input.equals("6") || input.equalsIgnoreCase("D") || input.equalsIgnoreCase("Draw")) {
            drawBoard.drawBoard(gameData.game(), playColor, null);
            return "";
        } else if (!isObserver && input.equals("7") || input.equalsIgnoreCase("M") || input.equalsIgnoreCase("Move")) {
            return "Message: Not implemented yet";
        } else if (!isObserver && input.equals("8") || input.equalsIgnoreCase("R") || input.equalsIgnoreCase("Resign")) {
            return "Message: Not implemented yet";
        } else {
            return "invalid input";
        }
    }

    public void printPrompt() {
        System.out.print(" [PLAYING GAME]>>> ");
    }
}
