package connection;

import messages.ServerMessage;

public interface ServerMessageObserver {
    public void notify(ServerMessage message);
}
