package commands;

public class UserGameCommand {

    private CommandType commandType;
    private String authToken;
    private int gameID;

    public UserGameCommand(CommandType commandType, String authToken, int gameID) {
        this.commandType = commandType;
        this.authToken = authToken;
        this.gameID = gameID;
    }

    public enum CommandType {
        CONNECT,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getAuthToken() {
        return authToken;
    }

    public int getGameID() {
        return gameID;
    }
}
