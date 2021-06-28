package commands;

import exceptions.NoDataException;
import exceptions.NoSuchStatementException;
import response.Response;
import utils.CollectionManager;

import java.io.IOException;
import java.sql.SQLException;

public class CommandUpdate implements Command {
    private final CollectionManager manager;
    private final RequestData requestData;

    public CommandUpdate(CollectionManager manager, RequestData requestData) {
        this.manager = manager;
        this.requestData = requestData;
    }

    @Override
    public Response execute(String[] args) throws IOException, NoDataException, ClassNotFoundException, SQLException, NoSuchStatementException {
        int id = Integer.parseInt(args[0]);
        manager.update(requestData, id);
        return new Response("Обновлён элемент с id " + id);
    }

    @Override
    public String getDescription() {
        return "Обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String getUsage() {
        return "Использование: update id";
    }
}
