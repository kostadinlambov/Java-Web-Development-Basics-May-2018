package javache;

import javache.handlers.ConnectionHandler;
import javache.handlers.request.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

public class Server {
    private ServerSocket server;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws IOException {
        this.server = new ServerSocket(this.port);
        this.server.setSoTimeout(WebConstants.SOCKET_TIMEOUT_MILLISECONDS);

        System.out.println(WebConstants.LISTENING_MESSAGE + this.port);

        while (true) {
            try (Socket clientSocket = this.server.accept()) {
                clientSocket.setSoTimeout(WebConstants.SOCKET_TIMEOUT_MILLISECONDS);

                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, new RequestHandler());
                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch (SocketTimeoutException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
