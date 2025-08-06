package websocket;

import exception.ResponseException;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

    private ConcurrentHashMap<Integer, ArrayList<Connection>> connectionMap;

    public ConnectionManager() {
        connectionMap = new ConcurrentHashMap<>();
    }

    public void add(int gameID, Session session, String username) throws ResponseException {
        try {
            Connection newConnection = new Connection(session, username);
            ArrayList<Connection> connectionList = connectionMap.get(gameID);
            if (connectionList == null) {
                connectionList = new ArrayList<>();
            }
            connectionList.add(newConnection);
            connectionMap.put(gameID, connectionList);
        } catch (Exception ex) {
            throw new ResponseException(ex.getMessage(), 500);
        }
    }

    public void remove(int gameID, Session session) throws ResponseException {
        try {
            ArrayList<Connection> connectionList = connectionMap.get(gameID);
            connectionList.remove(session);
        } catch (Exception ex) {
            throw new ResponseException(ex.getMessage(), 500);
        }
    }
}
