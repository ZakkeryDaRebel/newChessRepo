package ui;

import chess.ChessGame;
import chess.ChessPosition;
import connection.ServerFacade;
import model.GameData;

import java.util.Scanner;

public class ClientPLAY {

    private final ServerFacade serverFacade;
    private GameData gameData;
    private ChessGame.TeamColor playColor;
    private boolean isObserver;
    private final DrawBoard drawBoard;

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
            return "leave";
        } else if (input.equals("3")) {
            System.out.println(" Would you like your chess pieces to look like the chess piece icons, or use a text letter?");
            System.out.println(" - Enter \"1\", \"P\", or \"Piece\" if you would like your chess pieces to look like '" + EscapeSequences.BLACK_QUEEN + "'");
            System.out.println(" - Enter \"2\", \"T\", or \"Text\" if you would like your chess pieces to look like 'Q'");
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
            System.out.println(" What piece would you like to highlight?");
            System.out.println(" ~ Please input the column (a - h)");
            printPrompt();
            String choice = scan.nextLine();
            int col = getCol(choice);
            if (col == -1) {
                return "invalid input";
            }
            System.out.println(" ~ Please input the row (1-8)");
            printPrompt();
            choice = scan.nextLine();
            int row;
            try {
                row = Integer.parseInt(choice);
                if (row < 1 || row > 8) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                return "invalid input";
            }
            ChessPosition highlightPos = new ChessPosition(row, col);
            drawBoard.drawBoard(gameData.game(), playColor, highlightPos);

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

    public int getCol(String choice) {
        if (choice.equalsIgnoreCase("a")) {
            return 1;
        } else if (choice.equalsIgnoreCase("b")) {
            return 2;
        } else if (choice.equalsIgnoreCase("c")) {
            return 3;
        } else if (choice.equalsIgnoreCase("d")) {
            return 4;
        } else if (choice.equalsIgnoreCase("e")) {
            return 5;
        } else if (choice.equalsIgnoreCase("f")) {
            return 6;
        } else if (choice.equalsIgnoreCase("g")) {
            return 7;
        } else if (choice.equalsIgnoreCase("h")) {
            return 8;
        } else {
            return -1;
        }
    }
}
