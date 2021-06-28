import request.Request;
import request.RequestType;
import response.Response;
import response.ResponseType;
import utils.ConnectionManager;
import utils.ConsoleManager;
import utils.MarineBuilder;
import utils.NetworkManager;

import java.io.*;
import java.util.Arrays;

public class Application {

    private final ConsoleManager consoleManager;

    public Application() {
        consoleManager = new ConsoleManager();
    }

    public void run() throws IOException, ClassNotFoundException {
        NetworkManager networkManager = new NetworkManager(new ConnectionManager("localhost", 4445));
        String login = "";
        boolean success;
        do {
            consoleManager.print("Введите логин: ");
            login = consoleManager.read();
            consoleManager.print("Введите пароль: ");
            String pass = consoleManager.read();
            networkManager.send(new Request(login, pass));
            success = networkManager.receive().getSuccess();
            if (success) consoleManager.println("Успешно");
            else consoleManager.println("Неверный логин или пароль");
        } while (!success);

        while (true) {
            String[] input = consoleManager.read().toLowerCase().trim().split("\\s+");
            if (input[0].equals("exit")) {
                break;
            }
            networkManager.send(new Request(input[0], Arrays.copyOfRange(input, 1, input.length), login));

            Response response = networkManager.receive();
            if (response.getResponseType() == ResponseType.DATA) {
                networkManager.send(new Request(input[0], Arrays.copyOfRange(input, 1, input.length), new MarineBuilder(consoleManager).buildMarine(), login));
                response = networkManager.receive();
            }
            consoleManager.println(response.getMessage());
        }
    }
}
