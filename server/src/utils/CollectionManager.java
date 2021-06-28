package utils;

import commands.Command;
import commands.RequestData;
import exceptions.NoDataException;
import exceptions.NoSuchStatementException;
import marine.SpaceMarine;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class CollectionManager {

    private final ArrayDeque<SpaceMarine> marines;
    private final Collection<SpaceMarine> syncMarines;
    private final LocalDateTime creationDate;
    private final MarineReceiver marineReceiver;
    private final DBManager dbManager;

    public CollectionManager(DBManager dbManager) {
        marines = new ArrayDeque<>();
        syncMarines = Collections.synchronizedCollection(marines);
        marineReceiver = new MarineReceiver();
        creationDate = LocalDateTime.now();
        this.dbManager = dbManager;
    }

    public String getType(){
        return marines.getClass().getName();
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public int getSize(){
        return syncMarines.size();
    }

    public Stream<SpaceMarine> getStream(){
        return syncMarines.stream();
    }

    public void add(RequestData requestData) throws NoDataException, SQLException {
        dbManager.add(requestData);
        syncMarines.add(marineReceiver.buildMarine(requestData, dbManager.getId()));
    }

    public void update(RequestData requestData, Integer id) throws NoDataException, SQLException, NoSuchStatementException {
        dbManager.update(requestData, id);
        syncMarines.removeIf(marine -> marine.getId().equals(id));
        syncMarines.add(marineReceiver.buildMarine(requestData, id));
    }

    public void remove_by_id(RequestData requestData, Integer id) throws SQLException, NoSuchStatementException {
        dbManager.remove(requestData, id);
        syncMarines.removeIf(marine -> marine.getId().equals(id));
    }

    public void clear() {
        syncMarines.clear();
    }

    public void load() throws SQLException {
        dbManager.load(marines);
    }

    public void remove_first(RequestData requestData) throws SQLException, NoSuchStatementException {
        dbManager.remove(requestData, syncMarines.stream().min(SpaceMarine::compareTo).get().getId());
        syncMarines.remove(syncMarines.stream().min(SpaceMarine::compareTo).get());
    }

    public String add_if_min(RequestData requestData) throws NoDataException, SQLException {
            if (marineReceiver.buildMarine(requestData).compareTo(syncMarines.stream().min(SpaceMarine::compareTo).get()) < 0){
                dbManager.add(requestData);
                syncMarines.add(marineReceiver.buildMarine(requestData, dbManager.getId()));
                return "Элемент успешно добавлен в коллекцию";
            }
            else return "Элемент не был добавлен в коллекцию";
    }

    public void remove_greater(RequestData requestData) throws NoDataException {
        SpaceMarine comparable = marineReceiver.buildMarine(requestData);
        syncMarines.stream().filter(marine -> comparable.compareTo(marine) < 0).forEach(marine -> {
            try {
                dbManager.remove(requestData, marine.getId());
            } catch (SQLException | NoSuchStatementException e) {
                e.printStackTrace();
            }
        });
        syncMarines.removeIf(marine -> comparable.compareTo(marine) < 0);
    }

    public void execute_script(String script, Map<String, Command> commands, Stack<String> history){
        try {
            Scanner reader = new Scanner(new File(script));
            boolean skip = false;
            //Scanner prevScanner = serverInputManager.getInput();
            //serverInputManager.setInput(reader);
            history.push(script);
            while (reader.hasNextLine()) {
                String str = reader.nextLine().toLowerCase().trim();
                String[] input = str.split("\\s+");
                if (commands.containsKey(input[0])) {
                    if (input[0].equals("execute_script")) {
                        for (String filename : history) {
                            if (input[1].equals(filename)) {
                                System.out.println("Обнаружена рекурсия в файле " + history.peek() + ", пропуск команды");
                                skip = true;
                                break;
                            }
                        }
                    }
                    if (!skip) {
                        try {
                            commands.get(input[0]).execute(Arrays.copyOfRange(input, 1, input.length));
                        } catch (Exception e) {
                            System.out.println(commands.get(input[0]).getUsage());
                            break;
                        }
                    }
                } else if (!input[0].equals("")) {
                    System.out.println("Команда не найдена, введите help для списка доступных команд");
                    break;
                }
            }
            history.pop();
            //serverInputManager.setInput(prevScanner);
            reader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Указанный файл не найден");
        }
    }
}
