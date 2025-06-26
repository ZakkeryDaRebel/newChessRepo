package service;

import dataaccess.*;
import exception.ResponseException;
import model.UserData;
import requests.*;
import results.*;

import java.util.UUID;

public class UserService {

    private AuthDAO authDAO;
    private UserDAO userDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws ResponseException {
        if (registerRequest.password()==null || registerRequest.username()==null || registerRequest.email()==null) {
            throw new ResponseException("Error: Bad request", 400);
        }

        try {
            UserData user = userDAO.getUser(registerRequest.username());
            throw new ResponseException("Error: Already taken", 403);
        } catch (DataAccessException daex) {
            try {
                userDAO.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email());
                String authToken = generateAuthToken();
                authDAO.createAuth(registerRequest.username(), authToken);
                return new RegisterResult(authToken, registerRequest.username());
            } catch (Exception ex) {
                throw new ResponseException("Error: " + ex.getMessage(), 500);
            }
        }
    }

    public LoginResult login(LoginRequest loginRequest) throws ResponseException {
        return null;
    }

    public void logout(LogoutRequest logoutRequest) throws ResponseException {
        return;
    }

    public String generateAuthToken() {
        return UUID.randomUUID().toString();
    }


}
