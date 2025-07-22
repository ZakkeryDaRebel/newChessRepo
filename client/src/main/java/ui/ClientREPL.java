package ui;

import model.GameData;
import requests.*;
import results.*;

import java.util.Collection;
import java.util.Scanner;

public class ClientREPL {

    private UserState state = UserState.OUT;
    String authToken;
    Collection<GameData> gameList;
    ClientOUT clientOUT;
    //ClientIN clientIN;
    //ClientPLAY clientPLAY;

    public ClientREPL(String serverURL) {
        //Create connection to ServerFacade (pass in serverURL)
        clientOUT = new ClientOUT();
        //clientIN = new ClientIN();
        //clientPLAY = new ClientPLAY();
    }

    public enum UserState {
        OUT,
        IN,
        PLAY
    }

    public String stateToString() {
        switch(state) {
            case OUT: return "[LOGGED OUT]";
            case IN: return "[SIGNED IN]";
            case PLAY: return "[PLAYING GAME]";
            default: return "[null state]";
        }
    }

    public void run() {
        System.out.println(" Welcome to the Chess Game Interface (or CGI for short). Register or Login to start playing!");
        System.out.println(help());

        Scanner scan = new Scanner(System.in);
        String input = "";
        while (!input.equals("2") && !input.equalsIgnoreCase("Q") && !input.equalsIgnoreCase("Quit")) {
            printPrompt();
            input = scan.nextLine();
            System.out.println("User input: " + input);

            //Eval
            if (input.equals("1") || input.equalsIgnoreCase("H") || input.equalsIgnoreCase("Help")) {
                System.out.println(help());
                continue;
            }
            switch (state) {
                case OUT: {
                    String result = clientOUT.outEval(scan, input);
                    if (result.startsWith("authToken:")) {
                        state = UserState.IN;
                        authToken = result.substring(10);
                    } else if (result.equals("error")) {
                        error();
                    }
                    break;
                }
                case IN: inEval(scan, input); break;
                case PLAY: playEval(scan, input); break;
                default: error();
            }
        }
        System.out.println("\n Thanks for playing at the CGI! Have a great rest of your day, and hope to see you soon!");
    }

    public void printPrompt() {
        System.out.print("\n " + stateToString() + ">>> ");
    }

    public void inEval(Scanner scan, String input) {
        if (input.equals("2") || input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("Quit")) {
            LogoutRequest request = new LogoutRequest(authToken);
            //Send Logout request
            authToken = null;
        } else if (input.equals("3") || input.equalsIgnoreCase("G") || input.equalsIgnoreCase("Logout")) {
            LogoutRequest request = new LogoutRequest(authToken);
            //Send Logout request
            authToken = null;
            state = UserState.OUT;
        } else if (input.equals("4") || input.equalsIgnoreCase("C") || input.equalsIgnoreCase("Create")) {
            System.out.println("\n Please enter the name of the game you would like to create");
            printPrompt();
            String gameName = scan.nextLine();
            CreateGameRequest request = new CreateGameRequest(authToken, gameName);
            //Send CreateGame Request
        } else if (input.equals("5") || input.equalsIgnoreCase("L") || input.equalsIgnoreCase("List")) {
            ListGamesRequest request = new ListGamesRequest(authToken);
            //Sen ListGames Request
        } else if (input.equals("6") || input.equalsIgnoreCase("P") || input.equalsIgnoreCase("Play")) {
            System.out.println("\n Please enter the game number of the game you would like to play in");
            printPrompt();
            String gameNumberString = scan.nextLine();
            try {
                int gameNumber = Integer.parseInt(gameNumberString);
            } catch (Exception ex) {
                System.out.println("\n That is not a number. Please try again later");
                return;
            }

        } else if (input.equals("7") || input.equalsIgnoreCase("O") || input.equalsIgnoreCase("Observe")) {

        } else {
            error();
        }
    }

    public void playEval(Scanner scan, String input) {

    }

    public void error() {
        System.out.println("\n Sorry, that is an invalid input. Please don't add any spaces, and follow the help instructions");
        System.out.println(help());
    }

    public String help() {
        switch (state) {
            case OUT: return " - Enter \"1\", \"H\", or \"Help\" to show this list of actions you can take again"
                    + "\n" + " - Enter \"2\", \"Q\", or \"Quit\" to exit the Chess Game Interface (or CGI)"
                    + "\n" + " - Enter \"3\", \"R\", or \"Register\" to create a new user"
                    + "\n" + "         (You will need to supply a username, password, and email)"
                    + "\n" + " - Enter \"4\", \"L\", or \"Login\" to login as an existing user"
                    + "\n" + "         (You will need to supply a username and password)";

            case IN: return  " - Enter \"1\", \"H\", or \"Help\" to show this list of actions you can take again"
                    + "\n" + " - Enter \"2\", \"Q\", or \"Quit\" to logout and exit the Chess Game Interface (or CGI)"
                    + "\n" + " - Enter \"3\", \"G\", or \"Logout\" to logout and return to pre signed in state"
                    + "\n" + " - Enter \"4\", \"C\", or \"Create\" to create a new game"
                    + "\n" + "         (You will need to supply a game name)"
                    + "\n" + " - Enter \"5\", \"L\", or \"List\" to list all the games"
                    + "\n" + " - Enter \"6\", \"P\", or \"Play\" to join a game as a player"
                    + "\n" + "         (You will need to supply a game number and player color)"
                    + "\n" + " - Enter \"7\", \"O\", or \"Observe\" to join a game as an observer"
                    + "\n" + "         (You will need to supply a game number)";

            case PLAY:return " - Enter \"1\", \"H\", or \"Help\" to show this list of actions you can take again"
                    + "\n" + " - Enter \"2\", \"L\", or \"Leave\" to leave the game and return to signed in state"
                    + "\n" + " - Enter \"3\", \"I\", or \"Highlight\" to highlight the legal moves for a chess piece"
                    + "\n" + "         (You will need to supply the row and column of the piece you want to check)"
                    + "\n" + " - Enter \"4\", \"D\", or \"Draw\" to redraw the chess board"
                    + "\n" + " ~ The options below are only if you are playing in the game, not observing the game"
                    + "\n" + " - Enter \"5\", \"M\", or \"Move\" to move a chess piece"
                    + "\n" + "         (You will need to supply the row and column of the piece you want to move,"
                    + "\n" + "           and the row and column of where you want to move the piece to)"
                    + "\n" + " - Enter \"6\", \"R\", or \"Resign\" to resign the game";

            default: return  " We apologize, the CGI has last track of where you are in the system,"
                    + "\n" + " Please enter \"2\", \"Q\", or \"Quit\" to quit the CGI.";
        }
    }
}
