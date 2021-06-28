import utils.AuthManager;
import utils.DBManager;

import java.io.IOException;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws IOException, SQLException {
        Application server = new Application();
        server.run();
    }
}
