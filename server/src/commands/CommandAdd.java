package commands;

import exceptions.NoDataException;
import response.Response;
import utils.CollectionManager;

import java.io.IOException;
import java.sql.SQLException;

public class CommandAdd implements Command{
    private final CollectionManager manager;
    private final RequestData requestData;

    public CommandAdd(CollectionManager manager, RequestData requestData) {
        this.manager = manager;
        this.requestData = requestData;
    }

    @Override
    public Response execute(String[] args) throws IOException, NoDataException, ClassNotFoundException, SQLException {
        manager.add(requestData);
        return new Response("Элемент успешно добавлен в коллекцию");
    }

    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию";
    }

    @Override
    public String getUsage() {
        return null;
    }
}
