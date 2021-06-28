package utils;

import request.Request;
import response.Response;

import java.io.*;
import java.nio.ByteBuffer;

public class NetworkManager {
    private final ConnectionManager connectionManager;
    private final ByteBuffer buffer;
    ByteArrayOutputStream byteArrayOutputStream;

    public NetworkManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        byteArrayOutputStream = new ByteArrayOutputStream(4096);
        buffer = ByteBuffer.allocate(4096);
    }

    public void send(Request request) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        connectionManager.getChannel().send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), connectionManager.getSocket());
        byteArrayOutputStream.reset();
    }

    public Response receive() throws IOException, ClassNotFoundException {
        buffer.clear();
        connectionManager.getChannel().receive(buffer);
        return (Response) new ObjectInputStream(new ByteArrayInputStream(buffer.array())).readObject();
    }
}