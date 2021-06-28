package utils;

import commands.*;
import exceptions.NoDataException;
import exceptions.NoSuchCommandException;
import exceptions.NoSuchStatementException;
import request.MarineData;
import response.Response;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, Command> commands;
    private final RequestData requestData;


    public CommandManager(CollectionManager manager) {
        requestData = new RequestData();
        commands = new HashMap<>();
        commands.put("help", new CommandHelp(commands));
        commands.put("add", new CommandAdd(manager, requestData));
        commands.put("show", new CommandShow(manager));
        commands.put("info", new CommandInfo(manager));
        commands.put("update", new CommandUpdate(manager, requestData));
        commands.put("remove_by_id", new CommandRemoveById(manager, requestData));
        commands.put("clear", new CommandClear(manager));
        commands.put("execute_script", new CommandExecuteScript(manager, commands));
        commands.put("remove_first", new CommandRemoveFirst(manager, requestData));
        commands.put("add_if_min", new CommandAddIfMin(manager, requestData));
        commands.put("remove_greater", new CommandRemoveGreater(manager, requestData));
        commands.put("count_less_than_heart_count", new CommandCountLessThanHeartCount(manager));
        commands.put("count_greater_than_weapon_type", new CommandCountGreaterThanWeaponType(manager));
        commands.put("filter_by_heart_count", new CommandFilterByHeartCount(manager));
    }

    public Response executeCommand(String command, String[] args, MarineData marineData, String login) throws Exception {
        if (commands.containsKey(command)) {
            try {
                requestData.setMarineData(marineData);
                requestData.setLogin(login);
                return commands.get(command).execute(args);
            } catch (NoDataException e) {
               return new Response();
            }
            catch (NoSuchStatementException e) {
                throw e;
            }catch (Exception e){
                return new Response(commands.get(command).getUsage());
            }
        } else {
            throw new NoSuchCommandException();
        }
    }
}
