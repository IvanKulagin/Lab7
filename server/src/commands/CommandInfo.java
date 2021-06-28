package commands;

import response.Response;
import utils.CollectionManager;

import java.time.format.DateTimeFormatter;

public class CommandInfo implements Command {
    private final CollectionManager manager;

    public CommandInfo(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public Response execute(String[] args) {
        return new Response("Тип коллекции: " + manager.getType() + "\nДата инициализации: " + manager.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss")) + "\nКоличество элементов: " + manager.getSize());
    }

    @Override
    public String getDescription() {
        return "Вывести информацию о коллекци";
    }

    @Override
    public String getUsage() {
        return null;
    }
}
