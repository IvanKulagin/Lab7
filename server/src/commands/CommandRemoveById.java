package commands;

import exceptions.NoSuchStatementException;
import response.Response;
import utils.CollectionManager;

import java.sql.SQLException;

public class CommandRemoveById implements Command {
    private final RequestData requestData;
    private final CollectionManager manager;

    public CommandRemoveById(CollectionManager manager, RequestData requestData) {
        this.manager = manager;
        this.requestData = requestData;
    }

    @Override
    public Response execute(String[] args) throws SQLException, NoSuchStatementException {
        int id = Integer.parseInt(args[0]);
        manager.remove_by_id(requestData, id);
        return new Response("Элемент с id " + id + " успешно удалён");
    }

    @Override
    public String getDescription() {
        return "Удалить элемент из коллекции по его id";
    }

    @Override
    public String getUsage() {
        return "Использование: remove_by_id id";
    }
}
