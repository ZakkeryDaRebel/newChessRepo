package ui;

import chess.ChessGame;
import model.GameData;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.ListGamesRequest;
import requests.LogoutRequest;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientIN {

    String authToken;
    ArrayList<GameData> gameList;

    public ClientIN() {

    }

    public void printPrompt() {
        System.out.print(" [SIGNED IN]>>> ");
    }

    public void updateAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String inEval(Scanner scan, String input) {
        if (input.equals("2") || input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("Quit")) {
            if (logout().equals("success")) {
                return "quit";
            }
            return "Error: Failed to quit";
        } else if (input.equals("3") || input.equalsIgnoreCase("G") || input.equalsIgnoreCase("Logout")) {
            if (logout().equals("success")) {
                return "logout";
            }
            return "Error: Failed to logout";
        } else if (input.equals("4") || input.equalsIgnoreCase("C") || input.equalsIgnoreCase("Create")) {
            System.out.println("\n Please enter the name of the game you would like to create");
            printPrompt();
            String gameName = scan.nextLine();
            CreateGameRequest request = new CreateGameRequest(authToken, gameName);
            //Send CreateGame Request
            return "Message:" + "successfully created game";
        } else if (input.equals("5") || input.equalsIgnoreCase("L") || input.equalsIgnoreCase("List")) {
            ListGamesRequest request = new ListGamesRequest(authToken);
            //Sen ListGames Request
            return "Message:" + "no current games";
        } else if (input.equals("6") || input.equalsIgnoreCase("P") || input.equalsIgnoreCase("Play")) {
            int gameID;
            try {
                gameID = getGameNumber(scan);
            } catch (NumberFormatException ex) {
                return "Error: That is not a number. Please try again";
            }
            System.out.println(" Please enter \"W\" or \"White\" if you would like to play as White, or"
                      + "\n" + " Please enter \"B\" or \"Black\" if you would like to play as Black");
            printPrompt();
            String color = scan.nextLine();
            ChessGame.TeamColor playerColor;
            if (color.equalsIgnoreCase("W") || color.equalsIgnoreCase("White")) {
                playerColor = ChessGame.TeamColor.WHITE;
            } else if (color.equalsIgnoreCase("B") || color.equalsIgnoreCase("Black")) {
                playerColor = ChessGame.TeamColor.BLACK;
            } else {
                return "Error: Please enter a correct color option. Please try again";
            }
            JoinGameRequest request = new JoinGameRequest(authToken, playerColor, gameID);
            //Send JoinGame Request
            return "play: "+playerColor;
        } else if (input.equals("7") || input.equalsIgnoreCase("O") || input.equalsIgnoreCase("Observe")) {
            int gameID;
            try {
                gameID = getGameNumber(scan);
            } catch (NumberFormatException ex) {
                return "Error: That is not a number. Please try again";
            }
            //Send Websocket
            return "play: observer";
        } else {
            return "invalid input";
        }
    }

    public String logout() {
        LogoutRequest request = new LogoutRequest(authToken);
        //Send Logout request
        authToken = null;
        return "success";
    }

    public int getGameNumber(Scanner scan) throws NumberFormatException {
        System.out.println("\n Please enter the game number of the game you would like to play in");
        printPrompt();
        String gameNumberString = scan.nextLine();

        int gameNumber = Integer.parseInt(gameNumberString);

        GameData gameData = gameList.get(gameNumber);
        return gameData.gameID();
    }
}
