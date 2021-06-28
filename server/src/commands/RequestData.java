package commands;

import request.MarineData;

public class RequestData {
    private MarineData marineData;
    private String login;

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setMarineData(MarineData marineData) {
        this.marineData = marineData;
    }

    public MarineData getMarineData() {
        return marineData;
    }
}
