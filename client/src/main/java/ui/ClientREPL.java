package ui;

import connection.ServerFacade;
import exception.ResponseException;

import java.util.Scanner;

public class ClientREPL {

    private UserState state = UserState.OUT;
    ClientOUT clientOUT;
    ClientIN clientIN;
    //ClientPLAY clientPLAY;
    ServerFacade serverFacade;

    public ClientREPL(String serverURL) {
        serverFacade = new ServerFacade(serverURL);
        clientOUT = new ClientOUT(serverFacade);
        clientIN = new ClientIN();
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
            //System.out.println("User input: " + input);   //Testing purposes
            try {
                evalInput(scan, input);
            } catch (ResponseException ex) {
                printError(ex.getMessage());
            }
        }
        System.out.println("\n Thanks for playing at the CGI! Have a great rest of your day, and hope to see you soon!");
    }

    public void evalInput(Scanner scan, String input) throws ResponseException {
        if (input.equals("1") || input.equalsIgnoreCase("H") || input.equalsIgnoreCase("Help")) {
            System.out.println(help());
            return;
        }
        switch (state) {
            case OUT: {
                evalResult(clientOUT.outEval(scan, input));
                break;
            }
            case IN: {
                evalResult(clientIN.inEval(scan, input));
                break;
            }
            case PLAY: {
                evalResult(playEval(scan, input));
                break;
            }
            default: error();
        }
    }

    public void evalResult(String result) {
        if (result.startsWith("authToken:")) {
            state = UserState.IN;
            clientIN.updateAuthToken(result.substring(10));
            System.out.println("\n You have successfully signed into the CGI");
            System.out.println(help());
        } else if (result.equals("invalid input")) {
            error();
        } else if (result.equals("logout")) {
            state = UserState.OUT;
            System.out.println(" You have successfully logged out of the CGI");
            System.out.println(help());
        } else if (result.startsWith("Message:")) {
            printMessage(result.substring(8));
        } else if (result.startsWith("Error:")) {
            printError(result);
        }
        //"quit"
    }

    public void printMessage(String message) {
        System.out.println(" " + message);
    }

    public void printError(String error) {
        System.out.println(" " + error);
    }

    public void printPrompt() {
        System.out.print(" " + stateToString() + ">>> ");
    }



    public String playEval(Scanner scan, String input) {
        return "empty";
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
