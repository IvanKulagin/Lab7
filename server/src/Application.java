import request.Request;
import response.Response;
import utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    private final ExecutorService requestReceiver = Executors.newCachedThreadPool();
    private final ExecutorService requestHandler = Executors.newFixedThreadPool(10);

    private final CommandManager commandManager;
    private final NetworkManager networkManager;
    private Boolean running;

    public Application() throws IOException, SQLException {
        networkManager = new NetworkManager(new ConnectionManager(4445));
        DBManager dbManager = new DBManager();
        CollectionManager collectionManager = new CollectionManager(dbManager);
        commandManager = new CommandManager(collectionManager);
        collectionManager.load();
        running = true;
    }

    public void run() {
        AuthManager authManager = new AuthManager();
        boolean success = false;
        do {
            try {
                System.out.println("Waiting for login");
                Request request = networkManager.receive();
                success = authManager.checkUser(request.getLogin(), request.getPass());
                networkManager.send(new Response(success));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!success);

        Runnable r = () -> {
            try {
                while (running) {
                    System.out.println("Waiting");
                    Request request = networkManager.receive();
                    System.out.println(request.getCommand());
                    handleRequests(request);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        requestReceiver.submit(r);
    }

    public void handleRequests(Request request) {
        Runnable r = () -> {
            try {
                sendResponse(commandManager.executeCommand(request.getCommand(), request.getArgs(), request.getMarineData(), request.getLogin()));
            } catch (Exception e) {
                sendResponse(new Response(e.getMessage()));
            }
        };
        requestHandler.submit(r);
    }

    public void sendResponse(Response response) {
        Runnable r = () -> {
            try {
                networkManager.send(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread responseSender = new Thread(r);
        responseSender.start();
    }

    public void exit() {
        running = false;
    }
}
