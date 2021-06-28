package request;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = 7766749381571691204L;
    private final RequestType requestType;
    private String command;
    private String[] args;
    private MarineData marineData;
    private String login;
    private String pass;

    public Request(String login, String pass) {
        this.login = login;
        this.pass = pass;
        requestType = RequestType.LOGIN;
    }

    public Request(String command, String[] args, String login) {
        this.command = command;
        this.args = args;
        requestType = RequestType.COMMAND;
    }

    public Request(String command, String[] args, MarineData marineData, String login) {
        this.command = command;
        this.args = args;
        this.marineData = marineData;
        this.login = login;
        requestType = RequestType.DATA;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public MarineData getMarineData() {
        return marineData;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }
}
