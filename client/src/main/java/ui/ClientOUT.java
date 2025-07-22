package ui;

import requests.LoginRequest;
import requests.RegisterRequest;

import java.util.Scanner;

public class ClientOUT {

    public ClientOUT() {

    }

    public void printPrompt() {
        System.out.print("\n [LOGGED OUT]>>> ");
    }

    public String outEval(Scanner scan, String input) {
        if (input.equals("2") || input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("Quit")) {
            //Quit doesn't need to do anything when they haven't signed in yet
        } else if (input.equals("3") || input.equalsIgnoreCase("R") || input.equalsIgnoreCase("Register")) {
            LoginRequest loginReq = getLoginInfo(scan);
            System.out.println("\n Please enter your email");
            printPrompt();
            String email = scan.nextLine();
            RegisterRequest registerReq = new RegisterRequest(loginReq.username(), loginReq.password(), email);
            //Send Register Request
            //Get authToken from result
            String authToken = "1234";
            return "authToken:" + authToken;
        } else if (input.equals("4") || input.equalsIgnoreCase("L") || input.equalsIgnoreCase("Login")) {
            LoginRequest loginReq = getLoginInfo(scan);
            //Send Login Request
            //Get authToken from result
            String authToken = "1234";
            return "authToken:" + authToken;
        } else {
            return "error";
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
}
