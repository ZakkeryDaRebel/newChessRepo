package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {

    HashMap<String, AuthData> auths;

    public MemoryAuthDAO() {
        auths = new HashMap<>();
    }

    public void createAuth(String authToken, String username) {
        AuthData newAuth = new AuthData(authToken, username);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        AuthData auth = auths.get(authToken);
        if (auth == null) {
            throw new DataAccessException("Error: Auth does not exist");
        }
        return auth;
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        AuthData auth = auths.get(authToken);
        if (auth == null) {
            throw new DataAccessException("Error: Auth does not exist");
        }
        auths.remove(authToken);
    }

    public void clearAuths() {
        auths.clear();
    }
}
