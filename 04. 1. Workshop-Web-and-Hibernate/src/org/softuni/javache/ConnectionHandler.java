package org.softuni.javache;

import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.io.Reader;
import org.softuni.javache.io.Writer;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ConnectionHandler extends Thread {
        private static final String REQUEST_CONTENT_FAILURE_MESSAGE = "Failed to load request content.";
    private Socket clientSocket;

    private InputStream clientSocketInputStream;

    private OutputStream clientSocketOutputStream;

//    private CasebookApplication requestHandler;

    private Set<RequestHandler> requestHandlers;

    public ConnectionHandler(Socket clientSocket, Set<RequestHandler> requestHandlers){
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
            int counter = 0;

            while (counter++ < WebConstants.CONNECTION_KILL_LIMIT) {

                requestContent = Reader.readAllLines(this.clientSocketInputStream);
                if (requestContent.length() > 0) {
                    break;
                }
                System.out.println(counter);
            }

            if(requestContent == null){
                throw new NullPointerException(REQUEST_CONTENT_FAILURE_MESSAGE);
            }

            byte[] responseContent = null;

            for (RequestHandler requestHandler : requestHandlers) {
                responseContent = requestHandler.handleRequest(requestContent);

                if(requestHandler.hasIntercepted()){
                    break;
                }
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






