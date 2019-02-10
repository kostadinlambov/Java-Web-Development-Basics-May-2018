package org.softuni.javache;

import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.io.Reader;
import org.softuni.javache.io.Writer;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ConnectionHandler extends Thread {
    private static final int CONNECTION_KILL_LIMIT = 5000;

    private static final String REQUEST_CONTENT_LOADING_FAILURE_EXCEPTION_MESSAGE = "Failed loading request content.";

    private Socket clientSocket;

    private InputStream clientSocketInputStream;

    private OutputStream clientSocketOutputStream;

    private Set<RequestHandler> requestHandlers;

    public ConnectionHandler(Socket clientSocket, Set<RequestHandler> requestHandlers) {
        this.initializeConnection(clientSocket);
        this.requestHandlers = requestHandlers;
    }

    private void initializeConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.clientSocketInputStream = this.clientSocket.getInputStream();
            this.clientSocketOutputStream = this.clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String requestContent = null;

            int connectionReadTimer = 0;

            while(connectionReadTimer++ < CONNECTION_KILL_LIMIT) {
                requestContent = Reader.readAllLines(this.clientSocketInputStream);

                if(requestContent.length() > 0) break;
            }

            if(requestContent == null || requestContent.length() < 1) {
                throw new NullPointerException(REQUEST_CONTENT_LOADING_FAILURE_EXCEPTION_MESSAGE);
            }

            byte[] responseContent = null;

            for (RequestHandler requestHandler : this.requestHandlers) {
                responseContent = requestHandler.handleRequest(requestContent);

                if(requestHandler.hasIntercepted()) break;
            }

            Writer.writeBytes(responseContent, this.clientSocketOutputStream);
            this.clientSocketInputStream.close();
            this.clientSocketOutputStream.close();
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






