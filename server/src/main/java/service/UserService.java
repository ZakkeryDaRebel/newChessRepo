package service;

import dataaccess.*;
import requests.*;
import results.*;

public class UserService {

    private AuthDAO authDAO;
    private UserDAO userDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public RegisterResult register(RegisterRequest registerRequest) {
        return null;
    }

    public LoginResult login(LoginRequest loginRequest) {
        return null;
    }

    public void logout(LogoutRequest logoutRequest)  {
        return;
    }
}
