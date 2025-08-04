package messages;

public class ServerMessage {

    private ServerMessageType serverMessageType;

    public ServerMessage(ServerMessageType serverMessageType) {
        this.serverMessageType = serverMessageType;
    }

    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public ServerMessageType getServerMessageType() {
        return serverMessageType;
    }
}
