package commands;

import response.Response;
import utils.CollectionManager;

public class CommandClear implements Command {
    private final CollectionManager manager;

    public CommandClear(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public Response execute(String[] args) {
        manager.clear();
        return new Response("Коллекция очищена");
    }

    @Override
    public String getDescription() {
        return "Очистить коллекцию";
    }

    @Override
    public String getUsage() {
        return null;
    }
}
