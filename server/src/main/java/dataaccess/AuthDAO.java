package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void createAuth(String authToken, String username);
    AuthData getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    void clearAuths();
}
