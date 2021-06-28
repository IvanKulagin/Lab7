package commands;

import response.Response;
import utils.CollectionManager;

public class CommandCountLessThanHeartCount implements Command {
    private final CollectionManager manager;
    public CommandCountLessThanHeartCount(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public Response execute(String[] args) {
        return new Response("Количство элементов: " + manager.getStream().filter(marine -> marine.getHeartCount() < Integer.parseInt(args[0])).count());
    }

    @Override
    public String getDescription() {
        return "Вывести количество элементов, значение поля heartCount которых меньше заданного";
    }

    @Override
    public String getUsage() {
        return "Использование: count_less_than_heart_count heartCount";
    }
}
