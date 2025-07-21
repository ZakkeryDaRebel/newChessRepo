package ui;

import requests.LoginRequest;
import requests.RegisterRequest;

import java.util.Scanner;

public class ClientREPL {

    private UserState state = UserState.OUT;

    public ClientREPL(String serverURL) {
        //Create connection to ServerFacade (pass in serverURL)
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
            } else if (state != UserState.PLAY && input.equals("2") || input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("Quit")) {
                continue;
            }
            switch (state) {
                case OUT: outEval(scan, input); break;
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

    public void outEval(Scanner scan, String input) {
        if (input.equals("3") || input.equalsIgnoreCase("R") || input.equalsIgnoreCase("Register")) {
            LoginRequest loginReq = getLoginInfo(scan);
            System.out.println("\n Please enter your email");
            printPrompt();
            String email = scan.nextLine();
            RegisterRequest registerReq = new RegisterRequest(loginReq.username(), loginReq.password(), email);
            //Send Register Request
            state = UserState.IN;
        } else if (input.equals("4") || input.equalsIgnoreCase("L") || input.equalsIgnoreCase("Login")) {
            LoginRequest loginReq = getLoginInfo(scan);
            //Send Login Request
            state = UserState.IN;
        } else {
            error();
        }
    }

    public LoginRequest getLoginInfo(Scanner scan) {
        System.out.println("\n Please enter your username");
        printPrompt();
        String username = scan.nextLine();
        System.out.println("\n Please enter your password");
        printPrompt();
        String password = scan.nextLine();
        return new LoginRequest(username, password);
    }

    public void inEval(Scanner scan, String input) {

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
                    + "\n" + " - Enter \"3\", \"R\", or \"Register\" to create a new user (will need a username, password, and email)"
                    + "\n" + " - Enter \"4\", \"L\", or \"Login\" to login as an existing user (will need a username and password)";

            case IN: return  " - Enter \"1\", \"H\", or \"Help\" to show this list of actions you can take again"
                    + "\n" + " - Enter \"2\", \"Q\", or \"Quit\" to logout and exit the Chess Game Interface (or CGI)"
                    + "\n" + " - Enter \"3\", \"G\", or \"Logout\" to logout and return to pre signed in state"
                    + "\n" + " - Enter \"4\", \"C\", or \"Create\" to create a new game (will need a game name)"
                    + "\n" + " - Enter \"5\", \"L\", or \"List\" to list all the games"
                    + "\n" + " - Enter \"6\", \"P\", or \"Play\" to join a game as a player (will need a game number and player color)"
                    + "\n" + " - Enter \"7\", \"O\", or \"Observe\" to join a game as an observer (will need a game number)";

            case PLAY:return " - Enter \"1\", \"H\", or \"Help\" to show this list of actions you can take again"
                    + "\n" + " - Enter \"2\", \"L\", or \"Leave\" to leave the game and return to signed in state"
                    + "\n" + " - Enter \"3\", \"I\", or \"Highlight\" to highlight the legal moves for a chess piece (will need a row and column of the piece you want to check)"
                    + "\n" + " - Enter \"4\", \"D\", or \"Draw\" to redraw the chess board"
                    + "\n" + " ~ The options below are only if you are playing the game, not if you are observing the game"
                    + "\n" + " - Enter \"5\", \"M\", or \"Move\" to move a chess piece (will need a row and column of the piece you want to move, and the row and column of where you want to move it to"
                    + "\n" + " - Enter \"6\", \"R\", or \"Resign\" to resign the game";

            default: return " Null state, enter \"2\", \"Q\", or \"Quit\" to quit the CGI.";
        }
    }
}
