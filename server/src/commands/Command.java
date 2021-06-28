package commands;

import request.MarineData;
import response.Response;

public interface Command {
    Response execute(String[] args) throws Exception;
    String getDescription();
    String getUsage();
}
