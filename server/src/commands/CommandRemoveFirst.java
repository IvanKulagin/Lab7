package commands;

import exceptions.NoSuchStatementException;
import response.Response;
import utils.CollectionManager;

import java.sql.SQLException;

public class CommandRemoveFirst implements Command {
    private final RequestData requestData;
    private final CollectionManager manager;

    public CommandRemoveFirst(CollectionManager manager, RequestData requestData) {
        this.manager = manager;
        this.requestData = requestData;
    }

    @Override
    public Response execute(String[] args) throws SQLException, NoSuchStatementException {
        manager.remove_first(requestData);
        return new Response("Удалён первый элемент в коллекции");
    }

    @Override
    public String getDescription() {
        return "Удалить первый элемент из коллекции";
    }

    @Override
    public String getUsage() {
        return null;
    }
}
