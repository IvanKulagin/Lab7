package commands;

import exceptions.NoDataException;
import response.Response;
import utils.CollectionManager;

import java.io.IOException;

public class CommandRemoveGreater implements Command {
    private final CollectionManager manager;
    private final RequestData requestData;

    public CommandRemoveGreater(CollectionManager manager, RequestData requestData) {
        this.manager = manager;
        this.requestData = requestData;
    }

    @Override
    public Response execute(String[] args) throws IOException, NoDataException, ClassNotFoundException {
        manager.remove_greater(requestData);
        return new Response("Элементы были удалены из коллекции");
    }

    @Override
    public String getDescription() {
        return "Удалить из коллекции все элементы, превышающие заданный";
    }

    @Override
    public String getUsage() {
        return null;
    }
}
