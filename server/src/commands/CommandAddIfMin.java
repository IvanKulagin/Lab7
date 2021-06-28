package commands;

import exceptions.NoDataException;
import response.Response;
import utils.CollectionManager;

import java.io.IOException;
import java.sql.SQLException;

public class CommandAddIfMin implements Command {
    private final CollectionManager manager;
    private final RequestData requestData;

    public CommandAddIfMin(CollectionManager manager, RequestData requestData) {
        this.manager = manager;
        this.requestData = requestData;
    }

    @Override
    public Response execute(String[] args) throws IOException, NoDataException, ClassNotFoundException, SQLException {
        return new Response(manager.add_if_min(requestData));
    }

    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public String getUsage() {
        return null;
    }
}
