package commands;

import response.Response;

import java.util.Map;

public class CommandHelp implements Command {

    private final Map<String, Command> commands;

    public CommandHelp(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public Response execute(String[] args) {
        StringBuilder message = new StringBuilder();
        commands.forEach((key, value) -> message.append(key).append(": ").append(value.getDescription()).append("\n"));
        return new Response(message.toString());
    }

    @Override
    public String getDescription() {
        return "Вывести эту справку";
    }

    @Override
    public String getUsage() {
        return null;
    }
}
